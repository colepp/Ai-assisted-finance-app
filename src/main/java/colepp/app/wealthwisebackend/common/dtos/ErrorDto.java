package colepp.app.wealthwisebackend.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class ErrorDto {
    String message;
    LocalDateTime timestamp;
}
