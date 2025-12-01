package io.olmosjt.auth.controller;

import io.olmosjt.auth.domain.dto.Authentication;
import io.olmosjt.auth.domain.dto.LinkGoogleRequest;
import io.olmosjt.auth.domain.dto.RegisterRequest;
import io.olmosjt.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Authentication.Response> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication.Response> authenticate(
            @RequestBody Authentication.Request request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Authentication.Response> refreshToken(
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(authService.refreshToken(authHeader));
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("Hello " + userDetails.getUsername());
    }


    @PostMapping("/link-google")
    public ResponseEntity<?> linkGoogleAccount(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody LinkGoogleRequest request
    ) {
        authService.linkGoogleAccount(userDetails.getUsername(), request);
        return ResponseEntity.ok("Google account linked successfully");
    }

}
