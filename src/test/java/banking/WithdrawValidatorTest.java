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
	CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		withdrawValidator = new WithdrawValidator(bank);
		commandProcessor = new CommandProcessor(bank);
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

	@Test
	public void withdraw_zero_from_checking_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 0");

		assertTrue(actual);
	}

	@Test
	public void withdraw_checking_limit_from_checking_account_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 400");

		assertTrue(actual);
	}

	@Test
	public void withdraw_200_from_checking_account_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 200");

		assertTrue(actual);
	}

	@Test
	public void withdraw_twice_from_checking_account_is_valid() {
		boolean once = withdrawValidator.validate("withdraw 10001000 10");
		boolean twice = withdrawValidator.validate("withdraw 10001000 90");
		assertTrue(once && twice);
	}

	@Test
	public void withdrawing_more_money_than_balance_in_checking_is_valid() {
		bank.deposit("10001000", 10);
		boolean actual = withdrawValidator.validate("withdraw 10001000 400");

		assertTrue(actual);
	}

	@Test
	public void withdraw_a_small_amount_twice_from_checking_is_valid() {
		boolean one = withdrawValidator.validate("withdraw 10001000 1");
		boolean two = withdrawValidator.validate("withdraw 10001000 1");

		assertTrue(one && two);
	}

	@Test
	public void withdraw_zero_from_savings_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 20002000 0");

		assertTrue(actual);
	}

	@Test
	public void withdraw_500_from_savings_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 20002000 500");

		assertTrue(actual);
	}

	@Test
	public void withdraw_savings_limit_for_savings_account_is_valid() {
		boolean actual = withdrawValidator.validate("withdraw 20002000 1000");

		assertTrue(actual);
	}

	@Test
	public void withdrawing_twice_from_savings_account_is_invalid() {
		boolean one = withdrawValidator.validate("withdraw 20002000 100");
		commandProcessor.process("withdraw 20002000 100");
		boolean two = withdrawValidator.validate("withdraw 20002000 100");
		assertFalse(one && two);
	}

	@Test
	public void withdrawing_twice_from_savings_in_two_different_months_is_valid() {
		boolean one = withdrawValidator.validate("withdraw 20002000 100");
		commandProcessor.process("withdraw 20002000 100");
		bank.deposit("20002000", 1000);
		bank.pass(1);
		boolean two = withdrawValidator.validate("withdraw 20002000 100");
		commandProcessor.process("withdraw 20002000 100");

		assertTrue(one && two);
	}

	@Test
	public void withdrawing_twice_from_checking_in_two_different_months_is_valid() {
		boolean one = withdrawValidator.validate("withdraw 10001000 100");
		commandProcessor.process("withdraw 10001000 100");
		bank.deposit("10001000", 1000);
		bank.pass(1);
		boolean two = withdrawValidator.validate("withdraw 10001000 100");
		commandProcessor.process("withdraw 10001000 100");

		assertTrue(one && two);
	}

	@Test
	public void withdrawing_more_than_400_at_a_time_from_checking_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 500");
		assertFalse(actual);
	}

	@Test
	public void withdrawing_a_negative_value_from_a_checking_account_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 10001000 -12");

		assertFalse(actual);
	}

	@Test
	public void withdraw_more_than_1000_at_a_time_from_savings_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 20002000 1100");

		assertFalse(actual);
	}

	@Test
	public void withdraw_a_negative_value_from_a_savings_account_is_invalid() {
		boolean actual = withdrawValidator.validate("withdraw 20002000 -10000");

		assertFalse(actual);
	}

	@Test
	public void withdrawing_from_a_checking_after_1_month_passes_is_valid() {
		bank.deposit("10001000", 200);
		bank.pass(1);
		boolean actual = withdrawValidator.validate("withdraw 10001000 100");
		assertTrue(actual);
	}

	@Test
	public void withdrawing_from_a_savings_after_1_month_passes_is_valid() {
		bank.deposit("20002000", 500);
		bank.pass(1);
		boolean actual = withdrawValidator.validate("withdraw 20002000 100");
		assertTrue(actual);
	}

	@Test
	public void withdrawing_twice_from_a_savings_account_after_1_month_passes_is_invalid() {
		bank.deposit("20002000", 500);
		bank.pass(1);
		boolean first = withdrawValidator.validate("withdraw 20002000 100");
		commandProcessor.process("withdraw 20002000 100");
		boolean second = withdrawValidator.validate("withdraw 20002000 100");
		assertFalse(first && second);
	}

	@Test
	public void withdrawing_once_from_a_cd_account_after_12_months_pass_is_valid() {
		bank.pass(12);
		boolean actual = withdrawValidator.validate("withdraw 30003000 3000");
		assertTrue(actual);
	}

	@Test
	public void withdrawing_a_negative_value_from_a_cd_account_after_12_months_is_invalid() {
		bank.pass(12);
		boolean actual = withdrawValidator.validate("withdraw 30003000 -10010");
		assertFalse(actual);
	}

	@Test
	public void withdrawing_from_a_cd_account_before_12_months_is_invalid() {
		bank.pass(11);
		boolean actual = withdrawValidator.validate("withdraw 30003000 3000");
		assertFalse(actual);
	}

	@Test
	public void withdrawing_more_than_account_balance_from_cd_account_after_12_months_is_valid() {
		bank.pass(12);
		boolean actual = withdrawValidator.validate("withdraw 30003000 5000");
		assertTrue(actual);
	}

	@Test
	public void withdrawing_twice_from_cd_account_is_invalid() {
		bank.pass(12);
		boolean one = withdrawValidator.validate("withdraw 30003000 5000");
		commandProcessor.process("withdraw 30003000 5000");
		boolean two = withdrawValidator.validate("withdraw 30003000 5000");
		assertFalse(one && two);
	}

}
