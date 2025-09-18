package colepp.app.wealthwisebackend.users.excpetions;

public class PasswordMatchException extends RuntimeException {
    public PasswordMatchException(String message) {
        super(message);
    }
}
