package io.olmosjt.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.olmosjt.auth.domain.dto.Authentication;
import io.olmosjt.auth.domain.dto.LinkGoogleRequest;
import io.olmosjt.auth.domain.dto.RegisterRequest;
import io.olmosjt.common.entity.identity.UserEntity;
import io.olmosjt.auth.domain.repository.UserRepository;
import io.olmosjt.auth.security.JwtService;
import io.olmosjt.common.enums.UserRole;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    public Authentication.Response register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email is already in use.");
        }

        var user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.READER)
                .isActive(true)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return buildAuthResponse(user, jwtToken, refreshToken);
    }

    public Authentication.Response authenticate(Authentication.Request request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return buildAuthResponse(user, jwtToken, refreshToken);
    }


    public Authentication.Response refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Token");
        }

        // Extract the OLD refresh token
        String oldRefreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(oldRefreshToken);

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found")); // Changed exception for simplicity

            // Validate the user
            var userDetails = User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword() != null ? user.getPassword() : "")
                    .roles(user.getRole().name())
                    .build();

            if (jwtService.isTokenValid(oldRefreshToken, userDetails)) {
                // 1. Generate NEW Access Token
                var newAccessToken = jwtService.generateAccessToken(user);

                // 2. Generate NEW Refresh Token (Rotation)
                // This ensures the user gets a fresh 7-day window, and the token string changes.
                var newRefreshToken = jwtService.generateRefreshToken(user);

                // 3. Return BOTH new tokens
                return buildAuthResponse(user, newAccessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Token expired or invalid");
    }

    @Transactional
    public void linkGoogleAccount(String userEmail, LinkGoogleRequest request) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String googleSub = extractGoogleSubFromToken(request.idToken());

        if (userRepository.findByGoogleSub(googleSub).isPresent()) {
            throw new RuntimeException("This Google account is already linked to another user");
        }

        user.setGoogleSub(googleSub);
        userRepository.save(user);
    }

    // --- Helper Methods ---
    private Authentication.Response buildAuthResponse(UserEntity user, String jwtToken, String refreshToken) {
        return new Authentication.Response(
                jwtToken,
                refreshToken,
                new Authentication.UserInfo(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getAvatarUrl(),
                        user.getRole()
                )
        );
    }

    /**
     * Verifies the Google ID Token using the official Google API Client Library.
     * This checks the signature, expiration, and audience (Client ID).
     */
    private String extractGoogleSubFromToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                return payload.getSubject();
            } else {
                throw new RuntimeException("Invalid Google ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to verify Google ID Token", e);
        }
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
