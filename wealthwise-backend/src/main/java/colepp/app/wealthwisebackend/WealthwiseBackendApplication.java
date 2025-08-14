package colepp.app.wealthwisebackend;

import colepp.app.wealthwisebackend.common.services.PostmarkEmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WealthwiseBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WealthwiseBackendApplication.class, args);
    }

}
