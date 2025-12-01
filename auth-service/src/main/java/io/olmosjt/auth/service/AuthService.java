package io.olmosjt.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.olmosjt.auth.domain.UserRoleType;
import io.olmosjt.auth.domain.dto.Authentication;
import io.olmosjt.auth.domain.dto.LinkGoogleRequest;
import io.olmosjt.auth.domain.dto.RegisterRequest;
import io.olmosjt.common.entity.identity.UserEntity;
import io.olmosjt.auth.domain.repository.UserRepository;
import io.olmosjt.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            throw new RuntimeException("Email already in use");
        }

        var user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRoleType.READER)
                .isActive(true)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new Authentication.Response(jwtToken, refreshToken, user.getRole());
    }

    public Authentication.Response authenticate(Authentication.Request request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();

        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new Authentication.Response(jwtToken, refreshToken, user.getRole());
    }


    public Authentication.Response refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Token");
        }
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow();

            var userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword() != null ? user.getPassword() : "")
                    .roles(user.getRole().name())
                    .build();

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateAccessToken(user);
                return new Authentication.Response(accessToken, refreshToken, user.getRole());
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
}
