import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	// Withdraw values are 200
	// Deposit values are 1000

	Bank bank;
	BankAccount checking;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		checking = new Checking("10002000", 10);
	}

	@Test
	public void bank_has_no_accounts_when_created() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void add_one_account_to_bank() {
		bank.addAccount(checking.getAccountId(), checking);

		assertEquals(checking.getAccountId(), bank.getAccounts().get(checking.getAccountId()).getAccountId());
	}

	@Test
	public void add_two_accounts_to_bank() {
		bank.addAccount(checking.getAccountId(), checking);

		// second account creation
		BankAccount checking2 = new Checking("10002000", 10);
		bank.addAccount(checking2.getAccountId(), checking2);

		assertEquals(checking.getAccountId(), bank.getAccounts().get(checking.getAccountId()).getAccountId());
		assertEquals(checking2.getAccountId(), bank.getAccounts().get(checking2.getAccountId()).getAccountId());
	}

	@Test
	public void retrieve_an_account() {
		bank.addAccount(checking.getAccountId(), checking);

		assertEquals(checking, bank.retrieveAccount(checking.getAccountId()));
	}

	@Test
	public void deposit_money_through_bank() {
		bank.addAccount(checking.getAccountId(), checking);
		bank.deposit(checking.getAccountId(), BankAccountTest.TEST_DEPOSIT_VALUE);
		assertEquals(BankAccountTest.TEST_DEPOSIT_VALUE, bank.checkBalance(checking.getAccountId()));
	}

	@Test
	public void withdraw_money_through_bank() {
		bank.addAccount(checking.getAccountId(), checking);
		// depositing some money first to be able to see the money being withdrawn
		bank.deposit(checking.getAccountId(), BankAccountTest.TEST_DEPOSIT_VALUE);
		bank.withdraw(checking.getAccountId(), BankAccountTest.TEST_WITHDRAW_VALUE);

		assertEquals(BankAccountTest.TEST_DEPOSIT_VALUE - BankAccountTest.TEST_WITHDRAW_VALUE,
				bank.checkBalance(checking.getAccountId()));
	}

	@Test
	public void deposit_money_twice_through_bank() {
		bank.addAccount(checking.getAccountId(), checking);
		bank.deposit(checking.getAccountId(), BankAccountTest.TEST_DEPOSIT_VALUE);
		bank.deposit(checking.getAccountId(), BankAccountTest.TEST_DEPOSIT_VALUE);

		assertEquals(BankAccountTest.TEST_DEPOSIT_VALUE * 2, bank.checkBalance(checking.getAccountId()));
	}

	@Test
	public void withdraw_money_twice_through_bank() {
		bank.addAccount(checking.getAccountId(), checking);
		bank.deposit(checking.getAccountId(), BankAccountTest.TEST_DEPOSIT_VALUE);
		bank.withdraw(checking.getAccountId(), BankAccountTest.TEST_WITHDRAW_VALUE);
		bank.withdraw(checking.getAccountId(), BankAccountTest.TEST_WITHDRAW_VALUE);

		assertEquals(BankAccountTest.TEST_DEPOSIT_VALUE - (BankAccountTest.TEST_WITHDRAW_VALUE * 2),
				bank.checkBalance(checking.getAccountId()));
	}

}
