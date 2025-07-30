package colepp.app.wealthwisebackend.finance.exceptions;

public class FailedPlaidRequest extends RuntimeException {
    public FailedPlaidRequest(String message) {
        super(message);
    }
}
