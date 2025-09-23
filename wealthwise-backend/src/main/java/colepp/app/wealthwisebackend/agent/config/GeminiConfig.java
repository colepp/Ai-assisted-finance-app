package colepp.app.wealthwisebackend.agent.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public Client geminiClient(@Value("${gemini.client-id}") String apikey){
        return Client.builder().apiKey(apikey).build();
    }
}
