package cmartinez.demojwt.service;

import static org.junit.jupiter.api.Assertions.*;

import cmartinez.demojwt.strategies.validations.PasswordValidationStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class PasswordValidationStrategyTest {

    @Autowired
    private PasswordValidationStrategy passwordValidationStrategy;

    @Test
    void passwordIsValid() {
        assertTrue(passwordValidationStrategy.isValid("Abcdef1@"));
    }

    @Test
    void passwordIsInvalid() {
        assertFalse(passwordValidationStrategy.isValid("abcdef"));
    }
}