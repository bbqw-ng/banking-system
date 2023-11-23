package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {

	Bank bank;
	Checking checking;
	Savings savings;
	CD cd;
	WithdrawValidator withdrawValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		withdrawValidator = new WithdrawValidator(bank);
		checking = new Checking("10001000", 10);
		savings = new Savings("20002000", 10);
		cd = new CD("30003000", 10, 2000);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.addAccount(cd.getAccountId(), cd);
	}

	@Test
	public void just_withdraw_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw");

		assertFalse(actual);
	}

	@Test
	public void missing_id_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 900");

		assertFalse(actual);
	}

	@Test
	public void missing_amount_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000");

		assertFalse(actual);
	}

	@Test
	public void withdraw_is_spelled_wrong_is_invalid() {
		boolean actual = withdrawValidator.validate("withhaw 10001000 900");

		assertFalse(actual);
	}

	@Test
	public void withdraw_is_missing_in_command_is_invalid() {
		boolean actual = withdrawValidator.validate("10001000 900");

		assertFalse(actual);
	}

	@Test
	public void case_insensitivity_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 400");

		assertTrue(actual);
	}

	@Test
	public void id_is_non_numeric_is_invalid() {
		boolean actual = withdrawValidator.validate("deposit ABC 100");

		assertFalse(actual);
	}

	@Test
	public void id_and_amount_is_non_numeric_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw ABC A");

		assertFalse(actual);
	}

	@Test
	public void amount_is_non_numeric_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 A");

		assertFalse(actual);
	}

	@Test
	public void withdrawing_from_non_existing_account_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 10123340 100");

		assertFalse(actual);
	}
}
