package colepp.app.wealthwisebackend.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class UserLoginRequest {

    @NotBlank(message = "password Required")
    @Email
    private String email;

    @NotBlank(message = "Password Required")
    private String password;
}
