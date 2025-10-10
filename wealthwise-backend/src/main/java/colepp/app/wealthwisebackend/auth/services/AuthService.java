package colepp.app.wealthwisebackend.auth.services;

import colepp.app.wealthwisebackend.auth.dtos.UserLoginResponse;
import colepp.app.wealthwisebackend.auth.dtos.UserLoginRequest;
import colepp.app.wealthwisebackend.common.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserLoginResponse login(UserLoginRequest userLoginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword())
        );
        return new UserLoginResponse(
            jwtService.generateAccessToken(userLoginRequest.getEmail()),
            jwtService.generateRefreshToken(userLoginRequest.getEmail()));
    }
}
