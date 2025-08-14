package colepp.app.wealthwisebackend.common.services;

import org.springframework.stereotype.Component;

@Component
public interface EmailService {
    void sendGenericMessage(String to,String from, String subject, String text);
    void sendRegistrationMessage(String name,String to, String from, String alias);
}
