package banking;

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
		String actual = (MOCK_TEST_CASE.toLowerCase());
		assertEquals("create checking 10002000 7", actual);
	}

	@Test
	public void can_split_string() {
		String[] actual = commandValidator.stringParser(MOCK_TEST_CASE.toLowerCase());

		assertEquals("10002000", actual[2]);
	}

	@Test
	public void check_valid_id() {
		String[] parsedList = commandValidator.stringParser(MOCK_TEST_CASE.toLowerCase());
		boolean actual = commandValidator.checkValidId(parsedList, 2);

		assertTrue(actual);
	}

	@Test
	public void check_valid_apr() {
		String[] parsedList = commandValidator.stringParser(MOCK_TEST_CASE.toLowerCase());
		boolean actual = commandValidator.checkValidApr(parsedList);

		assertTrue(actual);
	}

	@Test
	public void check_create_command() {
		boolean actual = commandValidator.validate(MOCK_TEST_CASE);

		assertTrue(actual);
	}

	@Test
	public void check_valid_class() {
		String[] parsedList = commandValidator.stringParser(MOCK_TEST_CASE.toLowerCase());
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

	@Test
	public void spaces_before_create_is_invalid() {
		boolean actual = commandValidator.validate("   create 10001000 8   ");
		assertFalse(actual);
	}

	@Test
	public void spaces_between_create_and_id_is_invalid() {
		boolean actual = commandValidator.validate("create checking      10001000 8");
		assertFalse(actual);
	}

	@Test
	public void spaces_between_id_and_apr_is_invalid() {
		boolean actual = commandValidator.validate("create savings 0001000    3");
		assertFalse(actual);
	}

	@Test
	public void spaces_after_apr_is_valid() {
		boolean actual = commandValidator.validate("create cd 10001000 3 1000      ");
		assertTrue(actual);
	}

	@Test
	public void checking_pass_time_branch_with_something_that_is_not_pass_is_invalid() {
		boolean actual = commandValidator.validate("pash 1");
		assertFalse(actual);
	}

	@Test
	public void checking_with_negative_id_is_invalid() {
		String command = "deposit -10002000 500";
		String[] parsed = command.split(" ");
		boolean actual = commandValidator.checkValidId(parsed, 1);
		assertFalse(actual);
	}

	@Test
	public void providing_an_invalid_class_will_return_nothing() {
		String command = "create sushi";
		String[] parsed = command.split(" ");
		String actual = commandValidator.getClass(parsed);
		assertEquals("", actual);
	}

	@Test
	public void providing_an_invalid_class_will_return_false() {
		String command = "create sushi";
		String[] parsed = command.split(" ");
		boolean actual = commandValidator.checkClass(parsed);
		assertFalse(actual);
	}

}
