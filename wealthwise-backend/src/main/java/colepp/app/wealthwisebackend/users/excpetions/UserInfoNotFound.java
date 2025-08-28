package colepp.app.wealthwisebackend.users.excpetions;

public class UserInfoNotFound extends RuntimeException {
    public UserInfoNotFound(String message) {
        super(message);
    }
}
