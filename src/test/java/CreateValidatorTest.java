import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {

    public static final String MOCK_TEST_CASE = "CREATE CHECKING 10002000 7";
    CreateValidator createValidator;

    @BeforeEach
    public void Setup(){
        Bank bank = new Bank();
        createValidator = new CreateValidator(bank);
    }

    @Test
    public void can_turn_command_lower_case(){
        String actual = createValidator.turnLowerCase(MOCK_TEST_CASE);
        assertEquals("create checking 10002000 7", actual);
    }

    @Test
    public void can_split_string(){
        String convert = createValidator.turnLowerCase(MOCK_TEST_CASE);
        String[] actual = createValidator.stringParser(convert);

        assertEquals("checking", actual[1]);
    }

    @Test
    public void check_valid_id(){
        String[] parsedList = createValidator.stringParser(createValidator.turnLowerCase(MOCK_TEST_CASE));
        boolean actual = createValidator.checkValidId(parsedList);

        assertTrue(actual);
    }

    @Test
    public void check_valid_apr(){
        String[] parsedList = createValidator.stringParser(createValidator.turnLowerCase(MOCK_TEST_CASE));
        boolean actual = createValidator.checkValidApr(parsedList);

        assertTrue(actual);
    }


    @Test
    public void case_insensitive_create_command(){
        boolean actual = createValidator.validate("creAte chEcKinG 10002000 7");
        assertTrue(actual);
    }
}
