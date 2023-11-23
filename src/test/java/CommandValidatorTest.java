import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	public static final String MOCK_TEST_CASE = "CREATE CHECKING 10002000 7";
	CommandValidator commandValidator;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void can_turn_command_lower_case() {
		String actual = commandValidator.turnLowerCase(MOCK_TEST_CASE);
		assertEquals("create checking 10002000 7", actual);
	}

	@Test
	public void can_split_string() {
		String[] actual = commandValidator.stringParser(commandValidator.turnLowerCase(MOCK_TEST_CASE));

		assertEquals("10002000", actual[2]);
	}

	@Test
	public void check_valid_id() {
		String[] parsedList = commandValidator.stringParser(commandValidator.turnLowerCase(MOCK_TEST_CASE));
		boolean actual = commandValidator.checkValidId(parsedList);

		assertTrue(actual);
	}

	@Test
	public void check_valid_apr() {
		String[] parsedList = commandValidator.stringParser(commandValidator.turnLowerCase(MOCK_TEST_CASE));
		boolean actual = commandValidator.checkValidApr(parsedList);

		assertTrue(actual);
	}

	@Test
	public void check_create_command() {
		String[] parsedList = commandValidator.stringParser(commandValidator.turnLowerCase(MOCK_TEST_CASE));
		boolean actual = commandValidator.checkCreate(parsedList);

		assertTrue(actual);
	}

	@Test
	public void check_valid_class() {
		String[] parsedList = commandValidator.stringParser(commandValidator.turnLowerCase(MOCK_TEST_CASE));
		boolean actual = commandValidator.checkClass(parsedList);

		assertTrue(actual);
	}

	@Test
	public void case_insensitive_create_command_is_valid() {
		boolean actual = commandValidator.validate("creAte chEcKinG 10002000 7");

		assertTrue(actual);
	}

	@Test
	public void missing_create_is_invalid() {
		boolean actual = commandValidator.validate("checking 10002000 7");

		assertFalse(actual);
	}

	@Test
	public void only_create_is_invalid() {
		boolean actual = commandValidator.validate("create");

		assertFalse(actual);
	}

	@Test
	public void misspelled_create_is_invalid() {
		boolean actual = commandValidator.validate("crte 10002000 7");

		assertFalse(actual);
	}
}
