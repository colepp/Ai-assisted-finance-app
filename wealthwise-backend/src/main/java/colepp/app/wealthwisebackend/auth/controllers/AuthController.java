package colepp.app.wealthwisebackend.auth.controllers;

import colepp.app.wealthwisebackend.auth.dtos.UserLoginResponse;
import colepp.app.wealthwisebackend.auth.dtos.UserLoginRequest;
import colepp.app.wealthwisebackend.auth.services.AuthService;
import colepp.app.wealthwisebackend.common.dtos.ErrorDto;
import colepp.app.wealthwisebackend.common.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        var response = authService.login(userLoginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        var refreshToken = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
            .filter(cookie -> cookie.getName().equals("refreshToken"))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);
        if(refreshToken == null || !jwtService.isValidToken(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var accessToken = jwtService.generateAccessToken(jwtService.getEmailFromToken(refreshToken));
        return ResponseEntity.ok(Map.of("accessToken",accessToken));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(e.getMessage(), LocalDateTime.now()));
    }

}
