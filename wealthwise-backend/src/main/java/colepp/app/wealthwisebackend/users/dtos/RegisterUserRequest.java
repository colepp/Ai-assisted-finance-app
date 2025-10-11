package colepp.app.wealthwisebackend.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255,message = "Name must be less than 255 characters")
    public String name;

    @NotBlank(message = "Email is required")
    @Email(message="Email must be valid")
    public String email;

    @NotBlank(message = "Password is Required ")
    @Size(min = 6,max= 25,message = "Password mus be between 6 to 25 characters")
    public String password;

    @NotBlank(message = "Password connfirmation is Required ")
    @Size(min = 6,max= 25,message = "Password mus be between 6 to 25 characters")
    @JsonProperty("confirm_password")
    public String confirmPassword;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    public String phoneNumber;
}
