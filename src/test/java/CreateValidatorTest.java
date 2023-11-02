import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    CreateValidator validator;

    @BeforeEach
    public void Setup(){
        Bank bank = new Bank();
        validator = new CreateValidator(bank);
    }

    @Test
    public void case_insensitive_create_command(){
        boolean actual = CreateValidator.validate("creAte chEcKinG 10002000 7");
        assertTrue(actual);
    }
}
