package io.olmosjt.auth.config;

import io.olmosjt.common.entity.identity.UserEntity;
import io.olmosjt.auth.domain.repository.UserRepository;
import io.olmosjt.common.security.JwtService;
import io.olmosjt.common.enums.UserRole;
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
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 1. Extract Data
        assert oAuth2User != null;
        String email = oAuth2User.getAttribute("email");
        String googleSub = oAuth2User.getAttribute("sub");
        String picture = oAuth2User.getAttribute("picture");

        // Google specific attributes for split names
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        // Fallback if family_name is missing (sometimes happens with limited scopes)
        if (firstName == null) {
            firstName = oAuth2User.getAttribute("name");
        }

        final String finalFirstName = firstName;
        final String finalLastName = lastName;

        // 2. Find or Create/Update User
        UserEntity userEntity = userRepository.findByEmail(email)
                .map(existingUser -> {
                    boolean changed = false;
                    if (existingUser.getGoogleSub() == null) {
                        existingUser.setGoogleSub(googleSub);
                        changed = true;
                    }
                    // Update Avatar if missing
                    if (existingUser.getAvatarUrl() == null && picture != null) {
                        existingUser.setAvatarUrl(picture);
                        changed = true;
                    }
                    // Update names if missing
                    if (existingUser.getFirstName() == null && finalFirstName != null) {
                        existingUser.setFirstName(finalFirstName);
                        changed = true;
                    }
                    if (existingUser.getLastName() == null && finalLastName != null) {
                        existingUser.setLastName(finalLastName);
                        changed = true;
                    }
                    return changed ? userRepository.save(existingUser) : existingUser;
                })
                .orElseGet(() -> {
                    UserEntity newUser = UserEntity.builder()
                            .email(email)
                            .googleSub(googleSub)
                            .firstName(finalFirstName)
                            .lastName(finalLastName)
                            .avatarUrl(picture)
                            .role(UserRole.READER)
                            .isActive(true)
                            .build();
                    return userRepository.save(newUser);
                });

        // 3. Generate Tokens
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        // 4. Redirect
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
