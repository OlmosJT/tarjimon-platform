package io.olmosjt.auth.config;

import io.olmosjt.auth.domain.UserRoleType;
import io.olmosjt.common.entity.identity.UserEntity;
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
        UserEntity userEntity = userRepository.findByEmail(email)
                .map(existingUserEntity -> {
                    // Update Google Sub if missing (Linking account logic for existing email users)
                    if (existingUserEntity.getGoogleSub() == null) {
                        existingUserEntity.setGoogleSub(googleSub);
                        return userRepository.save(existingUserEntity);
                    }
                    return existingUserEntity;
                })
                .orElseGet(() -> {
                    // Register new user
                    UserEntity newUserEntity = UserEntity.builder()
                            .email(email)
                            .googleSub(googleSub)
                            .role(UserRoleType.READER)
                            .isActive(true)
                            .build();
                    return userRepository.save(newUserEntity);
                });

        // 2. Generate Tokens
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        // 3. Redirect to Frontend with tokens (or use a cookie approach)
        // Adjust this URL to your Frontend (tarjimon.io)
        String targetUrl = UriComponentsBuilder.fromUriString("https://tarjimon.io/oauth2/redirect")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
