package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	Bank bank;
	DepositValidator depositValidator;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		checking = new Checking("10001000", 10);
		savings = new Savings("20002000", 10);
		cd = new CD("30003000", 10, 2000);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.addAccount(cd.getAccountId(), cd);
		depositValidator = new DepositValidator(bank);
	}

	@Test
	public void just_deposit_is_invalid() {
		boolean actual = depositValidator.validate("deposit");

		assertFalse(actual);
	}

	@Test
	public void missing_id_is_invalid() {
		boolean actual = depositValidator.validate("deposit 900");

		assertFalse(actual);
	}

	@Test
	public void missing_amount_is_invalid() {
		boolean actual = depositValidator.validate("deposit 10001000");

		assertFalse(actual);
	}

	@Test
	public void deposit_is_spelled_wrong_is_invalid() {
		boolean actual = depositValidator.validate("depsit 10001000 900");

		assertFalse(actual);
	}

	@Test
	public void deposit_is_missing_in_command_is_invalid() {
		boolean actual = depositValidator.validate("10001000 900");

		assertFalse(actual);
	}

	@Test
	public void case_insensitivity_is_valid() {
		boolean actual = depositValidator.validate("dePoSIt 10001000 900");

		assertTrue(actual);
	}

	@Test
	public void id_is_non_numeric_is_invalid() {
		boolean actual = depositValidator.validate("deposit ABC 100");

		assertFalse(actual);
	}

	@Test
	public void id_and_amount_is_non_numeric_is_invalid() {
		boolean actual = depositValidator.validate("deposit ABC A");

		assertFalse(actual);
	}

	@Test
	public void amount_is_non_numeric_is_invalid() {
		boolean actual = depositValidator.validate("deposit 10001000 A");

		assertFalse(actual);
	}

	@Test
	public void money_can_be_deposited_into_checking_account_is_valid() {
		boolean actual = depositValidator.validate("Deposit 10001000 800");

		assertTrue(actual);
	}

	@Test
	public void money_can_be_deposited_over_account_limit_is_invalid() {
		boolean actual = depositValidator.validate("deposit 10001000 1200");

		assertFalse(actual);
	}

	@Test
	public void negative_amounts_of_money_being_deposited_is_invalid() {
		boolean actual = depositValidator.validate("deposit 10001000 -1200");

		assertFalse(actual);
	}

	@Test
	public void zero_amount_of_money_being_deposited_is_valid() {
		boolean actual = depositValidator.validate("deposit 10001000 0");

		assertTrue(actual);
	}

	@Test
	public void money_can_be_deposited_into_savings_account_is_valid() {
		boolean actual = depositValidator.validate("Deposit 20002000 800");

		assertTrue(actual);
	}

	@Test
	public void money_can_be_deposited_over_savings_account_limit_is_invalid() {
		boolean actual = depositValidator.validate("deposit 20002000 2600");

		assertFalse(actual);
	}

	@Test
	public void money_can_be_deposited_under_savings_account_minimum_is_invalid() {
		boolean actual = depositValidator.validate("deposit 20002000 -2600");

		assertFalse(actual);
	}

	@Test
	public void money_can_be_deposited_into_cd_account_is_invalid() {
		boolean actual = depositValidator.validate("deposit 30003000 2600");

		assertFalse(actual);

	}

	@Test
	public void negative_money_can_be_deposited_into_cd_account_is_invalid() {
		boolean actual = depositValidator.validate("deposit 30003000 -2600");

		assertFalse(actual);
	}

	@Test
	public void savings_and_checking_can_be_deposited_money_concurrently_is_valid() {
		boolean AccountOne = depositValidator.validate("deposit 10001000 1000");
		boolean AccountTwo = depositValidator.validate("deposit 20002000 1000");

		assertTrue(AccountTwo && AccountOne);
	}

	@Test
	public void checking_unique_id_that_is_all_zeroes_is_valid() {
		Checking checking2 = new Checking("00000000", 8);
		bank.addAccount("00000000", checking2);
		boolean actual = depositValidator.validate("deposit 00000000 1000");

		assertTrue(actual);
	}

	@Test
	public void depositing_2500_into_savings_is_valid() {
		boolean actual = depositValidator.validate("deposit 20002000 2500");
		assertTrue(actual);
	}

	@Test
	public void depositing_0_into_savings_is_valid() {
		boolean actual = depositValidator.validate("deposit 20002000 0");
		assertTrue(actual);
	}

	@Test
	public void depositing_into_an_account_with_negative_id_is_invalid() {
		boolean actual = depositValidator.validate("deposit -10001000 0");
		assertFalse(actual);
	}

	@Test
	public void depositing_into_an_account_with_a_negative_value_is_invalid() {
		boolean actual = depositValidator.validate("deposit 10001000 -10");
		assertFalse(actual);
	}

	@Test
	public void depositing_into_an_account_that_has_a_decimal_id_is_invalid() {
		boolean actual = depositValidator.validate("deposit 1000.2 100");
		assertFalse(actual);
	}
}
