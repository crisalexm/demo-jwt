package cmartinez.demojwt.service;

import cmartinez.demojwt.strategies.validations.ValidationStrategy;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    private final ValidationStrategy emailValidationStrategy;
    private final ValidationStrategy passwordValidationStrategy;

    public ValidationService(ValidationStrategy emailValidationStrategy, ValidationStrategy passwordValidationStrategy) {
        this.emailValidationStrategy = emailValidationStrategy;
        this.passwordValidationStrategy = passwordValidationStrategy;
    }

    public boolean isValidEmail(String email) {
        return emailValidationStrategy.isValid(email);
    }

    public boolean isValidPassword(String password) {
        return passwordValidationStrategy.isValid(password);
    }
}
