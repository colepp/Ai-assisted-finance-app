package colepp.app.wealthwisebackend.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostmarkRegisterTemplateDto {
    private String name;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("login_url")
    private String loginUrl;

    @JsonProperty("action_url")
    private String actionUrl;

    private String username;
}
