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

}
