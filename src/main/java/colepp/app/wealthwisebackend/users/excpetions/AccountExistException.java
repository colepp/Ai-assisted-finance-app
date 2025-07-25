package colepp.app.wealthwisebackend.users.excpetions;

public class AccountExistException extends RuntimeException {
    public AccountExistException(String message) {
        super(message);
    }
}
