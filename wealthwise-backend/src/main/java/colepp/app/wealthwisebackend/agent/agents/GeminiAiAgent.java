package colepp.app.wealthwisebackend.agent.agents;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary

@AllArgsConstructor
public class GeminiAiAgent implements AiAgent {

    private final Client geminiClient;

    @Override
    public String createEval(String requestType, String data) {
        GenerateContentResponse response = geminiClient.models.generateContent(
            "gemini-2.5-flash",
            "Create a evaluation based on the " + requestType + " data: " + data,
            null
        );
        return response.text();

    }

    @Override
    public String createReport(String requestType, String data) {
        return "";
    }
}
