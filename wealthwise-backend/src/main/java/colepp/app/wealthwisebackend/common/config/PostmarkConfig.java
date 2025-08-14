package colepp.app.wealthwisebackend.common.config;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class PostmarkConfig {

    @Value("${postmark.client-id}")
    private String postmarkApiKey;

    @Bean
    public PostMarkEmailUtility postMarkEmailUtility() {
        return new PostMarkEmailUtility(postmarkApiKey);
    }

    @Bean
    public ApiClient client() {
        return Postmark.getApiClient(postmarkApiKey);
    }


}
