package io.olmosjt.auth.config;

import io.olmosjt.auth.domain.UserRoleType;
import io.olmosjt.auth.domain.entity.User;
import io.olmosjt.auth.domain.repository.UserRepository;
import io.olmosjt.auth.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String googleSub = oAuth2User.getAttribute("sub"); // Google's unique ID

        // 1. Find or Create User
        User user = userRepository.findByEmail(email)
                .map(existingUser -> {
                    // Update Google Sub if missing (Linking account logic for existing email users)
                    if (existingUser.getGoogleSub() == null) {
                        existingUser.setGoogleSub(googleSub);
                        return userRepository.save(existingUser);
                    }
                    return existingUser;
                })
                .orElseGet(() -> {
                    // Register new user
                    User newUser = User.builder()
                            .email(email)
                            .googleSub(googleSub)
                            .role(UserRoleType.READER)
                            .isActive(true)
                            .build();
                    return userRepository.save(newUser);
                });

        // 2. Generate Tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // 3. Redirect to Frontend with tokens (or use a cookie approach)
        // Adjust this URL to your Frontend (tarjimon.io)
        String targetUrl = UriComponentsBuilder.fromUriString("https://tarjimon.io/oauth2/redirect")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
