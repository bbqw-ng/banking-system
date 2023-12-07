package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {
	public static final double TEST_DEPOSIT_VALUE = 1000;
	public static final double TEST_WITHDRAW_VALUE = 200;
	BankAccount checking;

	@BeforeEach
	public void setUp() {
		checking = new Checking("10002000", 10);
	}

	@Test
	public void checking_account_apr_is_supplied_apr_when_created() {
		double actual = checking.getApr();

		assertEquals(10, actual);
	}

	@Test
	public void checking_account_can_deposit() {
		checking.doDeposit(TEST_DEPOSIT_VALUE);
		double actual = checking.getBalance();

		assertEquals(TEST_DEPOSIT_VALUE, actual);
	}

	@Test
	public void checking_account_can_withdraw() {
		// *note to self ask if this is allowed since it does technically
		// help with the tests :)
		checking.doDeposit(TEST_DEPOSIT_VALUE);
		checking.doWithdraw(TEST_WITHDRAW_VALUE);
		double actual = checking.getBalance();

		assertEquals(TEST_DEPOSIT_VALUE - TEST_WITHDRAW_VALUE, actual);
	}

	@Test
	public void checking_account_cannot_withdraw_less_than_balance() {
		checking.doWithdraw(TEST_WITHDRAW_VALUE);
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void checking_account_can_have_two_consecutive_deposits() {
		checking.doDeposit(TEST_DEPOSIT_VALUE);
		checking.doDeposit(TEST_DEPOSIT_VALUE);
		double actual = checking.getBalance();

		assertEquals(TEST_DEPOSIT_VALUE * 2, actual);
	}

	@Test
	public void checking_account_can_two_consecutive_withdrawals() {
		checking.doDeposit(TEST_DEPOSIT_VALUE);
		checking.doWithdraw(TEST_WITHDRAW_VALUE);
		checking.doWithdraw(TEST_WITHDRAW_VALUE);
		double actual = checking.getBalance();

		assertEquals(TEST_DEPOSIT_VALUE - (TEST_WITHDRAW_VALUE * 2), actual);
	}

	@Test
	public void checking_account_is_created_with_id() {
		String actual = checking.getAccountId();

		assertEquals("10002000", actual);
	}

	@Test
	public void doWithdraw_does_not_withdraw_less_than_account_balance() {
		checking.doDeposit(1000);
		checking.doWithdraw(1002);
		assertEquals(0, checking.getBalance());
	}

}
