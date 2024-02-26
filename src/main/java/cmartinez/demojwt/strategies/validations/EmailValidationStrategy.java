package cmartinez.demojwt.strategies.validations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Service
public class EmailValidationStrategy implements ValidationStrategy{

        @Value("${usuario.email.regex}")
        private String emailRegex;
        @Override
        public boolean isValid(String email) {


            if (email == null) {
                return false;
            }
            Pattern pattern = Pattern.compile(emailRegex);
            return pattern.matcher(email).matches();
        }
}
