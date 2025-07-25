package colepp.app.wealthwisebackend.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String token;
}
