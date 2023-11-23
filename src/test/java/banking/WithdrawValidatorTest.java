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
	public void withdrawing_twice_from_savings_account_is_valid() {
		boolean one = withdrawValidator.validate("withdraw 20002000 100");
		boolean two = withdrawValidator.validate("withdraw 20002000 100");

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

	// validate savings rules: can only withdraw once a month passTime(1);
	// invalid if try to withdraw multiple times before passTime(1) repeats.
	// valid if try to withdraw once after passTime(1) repeats

	// validate CD rules:
	// no money can be withdrawn until after 12 months pass
	// invalid if try to withdrawn before then
	// you can only withdraw the entire balance of CD if chosen to withdrawn
	// invalid if wanting to withdraw less than cd bal.
	// you still cannot withdraw a negative value
	// withdrawing more than balance is valid -> balance = 0;
}
