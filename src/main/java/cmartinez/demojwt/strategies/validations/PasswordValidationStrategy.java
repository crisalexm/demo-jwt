package cmartinez.demojwt.strategies.validations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Service
public class PasswordValidationStrategy implements ValidationStrategy{
    @Value("${usuario.password.regex}")
    private String passwordRegex;
    @Override
    public boolean isValid(String password) {
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }
}
