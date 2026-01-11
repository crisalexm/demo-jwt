package cmartinez.demojwt.service;

import cmartinez.demojwt.strategies.validations.ValidationStrategy;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ValidationService {
    
    private final Map<ValidationType, ValidationStrategy> validationStrategies;

    public ValidationService(Map<ValidationType, ValidationStrategy> validationStrategies) {
        this.validationStrategies = Map.copyOf(validationStrategies);
    }

    public boolean validate(ValidationType type, String value) {
        ValidationStrategy strategy = validationStrategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No validation strategy found for type: " + type);
        }
        return strategy.isValid(value);
    }
    
    // Métodos de conveniencia (opcional)
    public boolean isValidEmail(String email) {
        return validate(ValidationType.EMAIL, email);
    }

    public boolean isValidPassword(String password) {
        return validate(ValidationType.PASSWORD, password);
    }
}
