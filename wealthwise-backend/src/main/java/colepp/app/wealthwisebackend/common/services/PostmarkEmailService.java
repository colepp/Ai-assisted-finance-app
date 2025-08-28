package colepp.app.wealthwisebackend.common.services;

import colepp.app.wealthwisebackend.common.config.PostMarkEmailUtility;
import colepp.app.wealthwisebackend.common.dtos.PostmarkRegisterTemplateDto;
import colepp.app.wealthwisebackend.users.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PostmarkEmailService implements EmailService {

    private final ApiClient client;
    private final PostMarkEmailUtility postmarkEmailUtility;
    private final UserRepository userRepository;

    @Override
    public void sendGenericMessage(String to, String from, String subject, String text) {
        try {
            var message = new Message(to, from, subject, text);
            message.setMessageStream("outbound");
            MessageResponse response = client.deliverMessage(message);
//            return response;
        } catch (IOException | PostmarkException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendRegistrationMessage(String name,String to, String from,String templateAlias) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var registerMessageTemplate = new TemplatedMessage(to, from, templateAlias, "outbound");

            var templateModel = PostmarkRegisterTemplateDto.builder()
                    .name(name)
                    .productName("wealthwise")
                    .loginUrl("http://localhost:5173/login")
                    .actionUrl("http://localhost:5173/link")
                    .username(to)
                    .build();

            registerMessageTemplate.setHeaders(postmarkEmailUtility.registryHeaders());
            registerMessageTemplate.setTemplateModel(objectMapper.writeValueAsString(templateModel));

            var response = client.deliverMessageWithTemplate(registerMessageTemplate);
        }catch (PostmarkException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
