package cmartinez.demojwt.service;

import static org.junit.jupiter.api.Assertions.*;
import cmartinez.demojwt.strategies.validations.EmailValidationStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class EmailValidationStrategyTest {

    @Autowired
    private EmailValidationStrategy emailValidationStrategy;

    @Test
    void emailIsValid() {
        assertTrue(emailValidationStrategy.isValid("test@example.com"), "Email is invalid");
    }

    @Test
    void emailIsInvalid() {
        assertFalse(emailValidationStrategy.isValid("testexample.com"), "Email is valid");
    }
}
