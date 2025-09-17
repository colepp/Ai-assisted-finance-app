package colepp.app.wealthwisebackend.agent.agents;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiAgent implements AiAgent {

    @Value("${openAI.client-id}")
    private String openAiKey;
    private OpenAIClient openAIClient = null;

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
