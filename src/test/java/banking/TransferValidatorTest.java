package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {

	Bank bank;
	TransferValidator transferValidator;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		transferValidator = new TransferValidator(bank);
		checking = new Checking("10001000", 5);
		savings = new Savings("20002000", 5);
		cd = new CD("30003000", 5, 1000);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.addAccount(cd.getAccountId(), cd);
	}

	@Test
	public void just_transfer_is_invalid() {
		boolean actual = transferValidator.validate("transfer");

		assertFalse(actual);
	}

	@Test
	public void transfer_misspelled_is_invalid() {
		boolean actual = transferValidator.validate("trannnnsfere 10001000 20002000 100");
		assertFalse(actual);
	}

	@Test
	public void empty_command_is_invalid() {
		boolean actual = transferValidator.validate("");

		assertFalse(actual);
	}

	@Test
	public void transfer_command_is_valid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 100");
		assertTrue(actual);
	}

	@Test
	public void missing_sender_id_is_invalid() {
		boolean actual = transferValidator.validate("transfer 20002000 50");

		assertFalse(actual);
	}

	@Test
	public void missing_receiver_id_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 50");

		assertFalse(actual);
	}

	@Test
	public void missing_amount_to_transfer_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000");

		assertFalse(actual);
	}

	@Test
	public void missing_transfer_is_invalid() {
		boolean actual = transferValidator.validate("10001000 20002000 50");

		assertFalse(actual);
	}

	@Test
	public void non_numeric_sender_id_is_invalid() {
		boolean actual = transferValidator.validate("transfer 1000A200 20002000 50");

		assertFalse(actual);
	}

	@Test
	public void non_numeric_receiver_id_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 20A32000 50");

		assertFalse(actual);
	}

	@Test
	public void non_numeric_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 AA");

		assertFalse(actual);
	}

	@Test
	public void transfer_between_checking_and_checking_is_valid() {
		Checking checking2 = new Checking("10002000", 9);
		bank.addAccount(checking2.getAccountId(), checking2);

		boolean actual = transferValidator.validate("transfer 10001000 10002000 50");
		assertTrue(actual);
	}

	@Test
	public void transfer_twice_between_checking_and_checking_is_valid() {
		Checking checking2 = new Checking("10002000", 9);
		bank.addAccount(checking2.getAccountId(), checking2);

		boolean one = transferValidator.validate("transfer 10001000 10002000 50");
		boolean two = transferValidator.validate("transfer 10001000 10002000 50");

		assertTrue(one && two);
	}

	@Test
	public void transfer_between_checking_and_checking_with_0_amount_is_valid() {
		Checking checking2 = new Checking("10002000", 9);
		bank.addAccount(checking2.getAccountId(), checking2);

		boolean actual = transferValidator.validate("transfer 10001000 10002000 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_between_checking_and_checking_with_max_checking_withdraw_is_valid() {
		Checking checking2 = new Checking("10002000", 9);
		bank.addAccount(checking2.getAccountId(), checking2);

		boolean actual = transferValidator.validate("transfer 10001000 10002000 400");
		assertTrue(actual);
	}

	@Test
	public void transfer_between_checking_and_savings_is_valid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 50");
		assertTrue(actual);
	}

	@Test
	public void transfer_between_checking_and_savings_with_0_is_valid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_between_checking_and_savings_with_max_checking_withdraw_value_is_valid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 400");
		assertTrue(actual);
	}

	@Test
	public void transfer_between_checking_and_savings_twice_is_valid() {
		boolean one = transferValidator.validate("transfer 10001000 20002000 200");
		boolean two = transferValidator.validate("transfer 10001000 20002000 100");
		assertTrue(one && two);
	}

	@Test
	public void transfer_negative_amount_between_checking_and_savings_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 -132");
		assertFalse(actual);
	}

	@Test
	public void transfer_negative_amount_between_checking_and_checking_is_invalid() {
		Checking checking2 = new Checking("10002000", 9);
		bank.addAccount(checking2.getAccountId(), checking2);

		boolean actual = transferValidator.validate("transfer 10001000 10002000 -123");

		assertFalse(actual);
	}

	@Test
	public void transfer_between_checking_and_checking_with_amount_over_withdraw_limit_is_invalid() {
		Checking checking2 = new Checking("10002000", 9);
		bank.addAccount(checking2.getAccountId(), checking2);

		boolean actual = transferValidator.validate("transfer 10001000 10002000 500");
		assertFalse(actual);
	}

	@Test
	public void transfer_between_checking_and_savings_with_amount_over_withdraw_limit_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 500");
		assertFalse(actual);
	}

	@Test
	public void transfer_between_savings_and_checking_is_valid() {
		boolean actual = transferValidator.validate("transfer 20002000 10001000 500");
		assertTrue(actual);
	}

	@Test
	public void transfer_savings_and_checking_with_0_amount_is_valid() {
		boolean actual = transferValidator.validate("transfer 20002000 10001000 0");
		assertTrue(actual);
	}

	@Test
	public void transfer_savings_and_checking_with_over_savings_limit_is_invalid() {
		boolean actual = transferValidator.validate("transfer 20002000 10001000 1100");
		assertFalse(actual);
	}

	@Test
	public void transfer_savings_and_savings_with_over_savings_limit_is_invalid() {
		Savings savings2 = new Savings("20003000", 9);
		bank.addAccount(savings2.getAccountId(), savings2);

		boolean actual = transferValidator.validate("transfer 20002000 20003000 1200");
		assertFalse(actual);
	}

	@Test
	public void transfer_savings_and_savings_is_valid() {
		Savings savings2 = new Savings("20003000", 9);
		bank.addAccount(savings2.getAccountId(), savings2);

		boolean actual = transferValidator.validate("transfer 20002000 20003000 500");
		assertTrue(actual);
	}

	@Test
	public void transfer_savings_and_savings_with_0_amount_is_valid() {
		Savings savings2 = new Savings("20003000", 9);
		bank.addAccount(savings2.getAccountId(), savings2);

		boolean actual = transferValidator.validate("transfer 20002000 20003000 0");
		assertTrue(actual);
	}

	@Test
	public void transferring_more_than_checking_sender_account_balance_will_leave_balance_at_0() {
		checking.doDeposit(100);
	}

	@Test
	public void transfer_savings_and_checking_with_maximum_savings_withdraw_amount_is_valid() {
		boolean actual = transferValidator.validate("transfer 20002000 10001000 1000");
		assertTrue(actual);
	}

	// need to create tests for cd account
	// savings into cd
	// checking into cd
	// need to make test for checking to checking with non-numeric amount
	// need to make test for checking to savings with non-numierc amount
	// need to make test for savings to savings with non-numeric amount
	// need to make test for savings to checking with non-numeric amount
	// need to make test for ids that are over the 8 dighits
	// need to make test for ids that are under 8 digits
	// need to make test for ^^^^^^ that are both sender and receiver
	// need to make test for no ids and only a transfer and balance
}
