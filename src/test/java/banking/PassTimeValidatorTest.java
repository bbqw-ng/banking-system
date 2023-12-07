package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {

	Bank bank;
	PassTimeValidator passTimeValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		passTimeValidator = new PassTimeValidator(bank);
	}

	@Test
	public void just_passtime_is_invalid() {
		boolean actual = passTimeValidator.validate("passtime");
		assertFalse(actual);
	}

	@Test
	public void empty_command_is_invalid() {
		boolean actual = passTimeValidator.validate("");
		assertFalse(actual);
	}

	@Test
	public void command_with_only_amount_is_invalid() {
		boolean actual = passTimeValidator.validate("13");
		assertFalse(actual);
	}

	@Test
	public void misspelled_passtime_is_invalid() {
		boolean actual = passTimeValidator.validate("paSEtME");
		assertFalse(actual);
	}

	@Test
	public void passtime_with_nonnumeric_amount_is_invalid() {
		boolean actual = passTimeValidator.validate("passtime AB");
		assertFalse(actual);
	}

	@Test
	public void case_insensitivity_is_valid() {
		boolean actual = passTimeValidator.validate("pasSTIME 50");
		assertTrue(actual);
	}

	@Test
	public void passtime_command_is_valid_with_correct_spelling_and_amount() {
		boolean actual = passTimeValidator.validate("passtime 30");
		assertTrue(actual);
	}

	@Test
	public void passtime_command_is_invalid_when_amount_is_0() {
		boolean actual = passTimeValidator.validate("passtime 0");
		assertFalse(actual);
	}

	@Test
	public void passtime_command_is_valid_when_amount_is_1() {
		boolean actual = passTimeValidator.validate("passtime 1");
		assertTrue(actual);
	}

	@Test
	public void passtime_command_is_valid_when_amount_is_60() {
		boolean actual = passTimeValidator.validate("passtime 60");
		assertTrue(actual);
	}

	@Test
	public void passtime_command_is_invalid_when_amount_is_negative() {
		boolean actual = passTimeValidator.validate("passtime -1");
		assertFalse(actual);
	}

	@Test
	public void passtime_command_is_invalid_when_amount_is_greater_than_60() {
		boolean actual = passTimeValidator.validate("passtime 61");
		assertFalse(actual);
	}

	@Test
	public void passtime_command_is_invalid_when_amount_is_a_decimal() {
		boolean actual = passTimeValidator.validate("passtime 1.5");
		assertFalse(actual);
	}

	@Test
	public void parse_decimal_to_int_gives_int() {
		String input = "passtime 1.5";
		String[] split = input.split(" ");
		boolean actual = passTimeValidator.checkMonthInt(split);
		assertFalse(actual);
	}

}
