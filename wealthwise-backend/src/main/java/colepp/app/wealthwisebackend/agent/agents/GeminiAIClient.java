package colepp.app.wealthwisebackend.agent.agents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiAIClient implements AiAgent {

    @Value("${gemini.client-id}")
    private String apiKey;
    @Value("${gemini.product-number}")
    private String productNumber;

    private final String model = "gemini-2.5-flash";
    private final String geminiUrl = "https://generativelanguage.googleapis.com/v1beta/models/\\\n" +
        "gemini-2.5-flash:generateConten";

    @Override
    public String createEval(String requestType, String data) {

        return "";
    }

    @Override
    public String createReport(String requestType, String data) {
        return "";
    }

    public enum RequestType {
        REPORT,
        EVALUATE
    }

}
