package colepp.app.wealthwisebackend.common.config;

import com.postmarkapp.postmark.client.data.model.message.Header;

import java.util.ArrayList;
import java.util.List;

public class PostMarkEmailUtility {

    private String clientId;

    public PostMarkEmailUtility(String clientId) {
        this.clientId = clientId;
    }

    public List<Header> registryHeaders() {
        List<Header> headers = new ArrayList<>();
//        headers.add(new Header("Content-Type", "application/json"));
        headers.add(new Header("Accept", "application/json"));
        headers.add(new Header("X-Postmark-Server-Token", clientId));
        return headers;
    }
}
