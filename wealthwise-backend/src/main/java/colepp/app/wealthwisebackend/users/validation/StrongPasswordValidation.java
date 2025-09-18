package colepp.app.wealthwisebackend.users.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidation implements ConstraintValidator<StrongPassword, String> {

    private final String PATTERN = "^(?=.*[A-Z])(?=.*\\\\d)(?=.*[^a-zA-Z0-9]).+$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        return s.matches(PATTERN);
    }
}
