package colepp.app.wealthwisebackend.auth.services;

import colepp.app.wealthwisebackend.auth.dtos.JwtResponseDto;
import colepp.app.wealthwisebackend.auth.dtos.UserLoginDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
