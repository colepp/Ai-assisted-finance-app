package colepp.app.wealthwisebackend.agent.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class GeminiResponseDto {

    private String model;
    private ArrayList<String> content;
    private ArrayList<String> parts;
}
