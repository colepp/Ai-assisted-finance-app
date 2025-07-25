package colepp.app.wealthwisebackend.auth.controllers;

import colepp.app.wealthwisebackend.auth.dtos.JwtResponseDto;
import colepp.app.wealthwisebackend.auth.dtos.UserLoginDto;
import colepp.app.wealthwisebackend.auth.services.AuthService;
import colepp.app.wealthwisebackend.common.dtos.ErrorDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody UserLoginDto userLoginRequest) {
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(e.getMessage(), LocalDateTime.now()));
    }

}
