package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {

	Bank bank;
	TransferValidator transferValidator;
	CommandProcessor commandProcessor;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		transferValidator = new TransferValidator(bank);
		commandProcessor = new CommandProcessor(bank);
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

	@Test
	public void transferring_between_cd_and_cd_is_invalid() {
		CD cd2 = new CD("30004000", 5, 1000);
		bank.addAccount(cd2.getAccountId(), cd2);
		boolean actual = transferValidator.validate("transfer 30003000 30004000 1000");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_cd_and_checking_is_invalid() {
		boolean actual = transferValidator.validate("transfer 30003000 10001000 1000");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_checking_and_cd_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 30003000 1000");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_cd_and_savings_is_invalid() {
		boolean actual = transferValidator.validate("transfer 30003000 20002000 1000");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_savings_and_cd_is_invalid() {
		boolean actual = transferValidator.validate("transfer 20002000 30003000 1000");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_checking_to_checking_with_non_numeric_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 10001000 AA");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_checking_to_savings_with_non_numeric_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 20002000 AA");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_savings_to_savings_with_non_numeric_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 20002000 20002000 BB");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_savings_to_checking_with_non_numeric_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 20002000 10001000 OP");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_invalid_sender_account_is_invalid() {
		boolean actual = transferValidator.validate("transfer 1003A183 20002000 100");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_invalid_receiver_account_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 1883AAA3 100");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_no_accounts_and_only_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_sender_account_with_more_than_8_digits_is_invalid() {
		boolean actual = transferValidator.validate("transfer 100020003000 10001000 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_sender_account_with_less_than_8_digits_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10002 10001000 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_receiver_account_with_more_than_8_digits_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 100020003000 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_receiver_account_with_less_than_8_digits_is_invalid() {
		boolean actual = transferValidator.validate("transfer 10001000 1 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_negative_sender_account_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer -10001000 20002000 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_between_negative_receiver_amount_is_invalid() {
		boolean actual = transferValidator.validate("transfer 20002000 -10001000 200");
		assertFalse(actual);
	}

	@Test
	public void transferring_before_and_after_a_month_passes_with_checking_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 10001000 20002000 300");
		bank.pass(1);
		boolean two = transferValidator.validate("transfer 10001000 20002000 300");
		assertTrue(one && two);
	}

	@Test
	public void transferring_before_and_after_a_month_passes_with_savings_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 20002000 10001000 300");
		bank.pass(1);
		boolean two = transferValidator.validate("transfer 20002000 10001000 300");
		assertTrue(one && two);
	}

	@Test
	public void transferring_before_and_after_a_month_passes_with_cd_is_invalid() {
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 30003000 20002000 300");
		bank.pass(1);
		boolean two = transferValidator.validate("transfer 30003000 20002000 300");
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_before_a_month_passes_with_checking_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 10001000 20002000 300");
		boolean two = transferValidator.validate("transfer 10001000 20002000 300");
		bank.pass(1);
		assertTrue(one && two);
	}

	@Test
	public void transferring_twice_before_a_month_passes_with_savings_is_invalid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 20002000 10001000 300");
		boolean two = transferValidator.validate("transfer 20002000 10001000 300");
		bank.pass(1);
		assertTrue(one && two);
	}

	@Test
	public void transferring_twice_before_a_month_passes_with_cd_is_invalid() {
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 30003000 20002000 300");
		boolean two = transferValidator.validate("transfer 30003000 20002000 300");
		bank.pass(1);
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_after_a_month_passes_with_checking_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		bank.pass(1);
		boolean one = transferValidator.validate("transfer 10001000 20002000 300");
		boolean two = transferValidator.validate("transfer 10001000 20002000 300");
		assertTrue(one && two);
	}

	@Test
	public void transferring_twice_after_a_month_passes_with_savings_is_invalid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		bank.pass(1);
		boolean one = transferValidator.validate("transfer 20002000 10001000 300");
		commandProcessor.process("transfer 20002000 10001000 300");
		boolean two = transferValidator.validate("transfer 20002000 10001000 300");
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_after_a_month_passes_with_cd_is_invalid() {
		bank.deposit("20002000", 1000);
		bank.pass(1);
		boolean one = transferValidator.validate("transfer 30003000 20002000 300");
		boolean two = transferValidator.validate("transfer 30003000 20002000 300");
		assertFalse(one && two);
	}

	@Test
	public void transferring_cd_after_12_months_pass_is_invalid() {
		bank.deposit("20002000", 1000);
		bank.pass(12);
		boolean actual = transferValidator.validate("transfer 30003000 20002000 300");
		assertFalse(actual);
	}

	@Test
	public void transferring_cd_before_12_months_pass_is_invalid() {
		bank.deposit("20002000", 1000);
		boolean actual = transferValidator.validate("transfer 30003000 20002000 300");
		bank.pass(12);
		assertFalse(actual);
	}

	@Test
	public void transferring_before_and_after_60_months_passes_with_checking_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 10001000 20002000 300");
		bank.pass(60);
		boolean two = transferValidator.validate("transfer 10001000 20002000 300");
		assertTrue(one && two);
	}

	@Test
	public void transferring_before_and_after_60_months_passes_with_savings_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 20002000 10001000 300");
		bank.pass(60);
		boolean two = transferValidator.validate("transfer 20002000 10001000 300");
		assertTrue(one && two);
	}

	@Test
	public void transferring_before_and_after_60_months_passes_with_cd_is_invalid() {
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 30003000 20002000 300");
		bank.pass(60);
		boolean two = transferValidator.validate("transfer 30003000 20002000 300");
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_before_60_months_passes_with_checking_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 10001000 20002000 300");
		boolean two = transferValidator.validate("transfer 10001000 20002000 300");
		bank.pass(60);
		assertTrue(one && two);
	}

	@Test
	public void transferring_twice_before_60_months_passes_with_savings_is_invalid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 20002000 10001000 300");
		commandProcessor.process("transfer 20002000 10001000 300");
		boolean two = transferValidator.validate("transfer 20002000 10001000 300");
		bank.pass(60);
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_before_60_months_passes_with_cd_is_invalid() {
		bank.deposit("20002000", 1000);
		boolean one = transferValidator.validate("transfer 30003000 20002000 300");
		boolean two = transferValidator.validate("transfer 30003000 20002000 300");
		bank.pass(60);
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_after_60_months_passes_with_checking_is_valid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		bank.pass(60);
		boolean one = transferValidator.validate("transfer 10001000 20002000 300");
		boolean two = transferValidator.validate("transfer 10001000 20002000 300");
		assertTrue(one && two);
	}

	@Test
	public void transferring_twice_after_60_months_passes_with_savings_is_invalid() {
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
		bank.pass(60);
		boolean one = transferValidator.validate("transfer 20002000 10001000 300");
		commandProcessor.process("transfer 20002000 10001000 300");
		boolean two = transferValidator.validate("transfer 20002000 10001000 300");
		assertFalse(one && two);
	}

	@Test
	public void transferring_twice_after_60_months_passes_with_cd_is_invalid() {
		bank.deposit("20002000", 1000);
		bank.pass(60);
		boolean one = transferValidator.validate("transfer 30003000 10001000 300");
		boolean two = transferValidator.validate("transfer 30003000 10001000 300");
		assertFalse(one && two);
	}
	// need to validate all the pass time rules.
	// checking infinite withdraws
	// savings 1 per month
	// cd cannot transfer at all.

}
