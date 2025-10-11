package colepp.app.wealthwisebackend.users.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
