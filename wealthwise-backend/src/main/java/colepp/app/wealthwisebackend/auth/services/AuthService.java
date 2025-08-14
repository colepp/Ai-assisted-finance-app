package colepp.app.wealthwisebackend.auth.services;

import colepp.app.wealthwisebackend.common.dtos.JwtResponseDto;
import colepp.app.wealthwisebackend.auth.dtos.UserLoginDto;
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

    public JwtResponseDto login(UserLoginDto userLoginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword())
        );
        return new JwtResponseDto(jwtService.generateToken(userLoginRequest.getEmail()));
    }
}
