package colepp.app.wealthwisebackend.agent.agents;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public interface AiAgent {


    public String createEval(String requestType,String data);
    public String createReport(String requestType,String data);

}
