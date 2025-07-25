package colepp.app.wealthwisebackend.users.excpetions;

import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
