package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferProcessorTest {
	Bank bank;
	TransferProcessor transferProcessor;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		transferProcessor = new TransferProcessor(bank);
		checking = new Checking("10001000", 1);
		savings = new Savings("20002000", 1);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.deposit("10001000", 1000);
		bank.deposit("20002000", 1000);
	}

	private double aprCalc(double balance, double apr) {
		double aprConvertToPercentage = apr / 100;
		double monthlyAprConvert = aprConvertToPercentage / 12;
		double monthlyBalanceGain = balance * monthlyAprConvert;
		return monthlyBalanceGain + balance;
	}

	@Test
	public void transfer_between_checking_and_savings() {
		transferProcessor.process("transfer 10001000 20002000 500");
		assertEquals(500, bank.getAccountById("10001000").getBalance());
		assertEquals(1500, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_between_savings_and_checking() {
		transferProcessor.process("transfer 20002000 10001000 500");
		assertEquals(1500, bank.getAccountById("10001000").getBalance());
		assertEquals(500, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_between_savings_and_savings() {
		Savings savings2 = new Savings("20003000", 1);
		bank.addAccount(savings2.getAccountId(), savings2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 20002000 20003000 500");
		assertEquals(500, bank.getAccountById("20002000").getBalance());
		assertEquals(1500, bank.getAccountById("20003000").getBalance());
	}

	@Test
	public void transfer_between_checking_and_checking() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 10001000 20003000 500");
		assertEquals(500, bank.getAccountById("10001000").getBalance());
		assertEquals(1500, bank.getAccountById("20003000").getBalance());
	}

	@Test
	public void transfer_max_savings_amount_to_checking() {
		transferProcessor.process("transfer 20002000 10001000 1000");
		assertEquals(2000, bank.getAccountById("10001000").getBalance());
		assertEquals(0, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_max_savings_amount_to_savings() {
		Savings savings2 = new Savings("20003000", 1);
		bank.addAccount(savings2.getAccountId(), savings2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 20002000 20003000 1000");
		assertEquals(0, bank.getAccountById("20002000").getBalance());
		assertEquals(2000, bank.getAccountById("20003000").getBalance());
	}

	@Test
	public void transfer_max_checking_amount_to_checking() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 10001000 20003000 400");
		assertEquals(600, bank.getAccountById("10001000").getBalance());
		assertEquals(1400, bank.getAccountById("20003000").getBalance());
	}

	@Test
	public void transfer_max_checking_amount_to_savings() {
		transferProcessor.process("transfer 10001000 20002000 400");
		assertEquals(600, bank.getAccountById("10001000").getBalance());
		assertEquals(1400, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_0_from_savings_to_checking() {
		transferProcessor.process("transfer 20002000 10001000 0");
		assertEquals(1000, bank.getAccountById("10001000").getBalance());
		assertEquals(1000, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_0_from_savings_to_savings() {
		Savings savings2 = new Savings("20003000", 1);
		bank.addAccount(savings2.getAccountId(), savings2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 20002000 20003000 0");
		assertEquals(1000, bank.getAccountById("20002000").getBalance());
		assertEquals(1000, bank.getAccountById("20003000").getBalance());
	}

	@Test
	public void transfer_0_from_checking_to_savings() {
		transferProcessor.process("transfer 10001000 20002000 0");
		assertEquals(1000, bank.getAccountById("10001000").getBalance());
		assertEquals(1000, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_0_from_checking_to_checking() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 10001000 20003000 0");
		assertEquals(1000, bank.getAccountById("10001000").getBalance());
		assertEquals(1000, bank.getAccountById("20003000").getBalance());
	}

	@Test
	public void transfer_from_savings_to_checking_before_and_after_a_month() {
		transferProcessor.process("transfer 20002000 10001000 100");
		bank.pass(1);
		transferProcessor.process("transfer 20002000 10001000 100");
		assertEquals(aprCalc(1100, 1) + 100, bank.getAccountById("10001000").getBalance());
		assertEquals(aprCalc(900, 1) - 100, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_from_savings_to_savings_before_and_after_a_month() {
		Savings savings2 = new Savings("20003000", 1);
		bank.addAccount(savings2.getAccountId(), savings2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 20002000 20003000 100");
		bank.pass(1);
		transferProcessor.process("transfer 20002000 20003000 100");
		assertEquals(aprCalc(1100, 1) + 100, bank.getAccountById("20003000").getBalance());
		assertEquals(aprCalc(900, 1) - 100, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_from_checking_to_savings_before_and_after_a_month() {
		transferProcessor.process("transfer 10001000 20002000 100");
		bank.pass(1);
		transferProcessor.process("transfer 10001000 20002000 100");
		assertEquals(aprCalc(1100, 1) + 100, bank.getAccountById("20002000").getBalance());
		assertEquals(aprCalc(900, 1) - 100, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void transfer_from_checking_to_checking_before_and_after_a_month() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 1000);
		transferProcessor.process("transfer 10001000 20003000 100");
		bank.pass(1);
		transferProcessor.process("transfer 10001000 20003000 100");
		assertEquals(aprCalc(1100, 1) + 100, bank.getAccountById("20003000").getBalance());
		assertEquals(aprCalc(900, 1) - 100, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void transfer_checking_to_checking_in_the_same_month() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 1000);
		bank.pass(1);
		transferProcessor.process("transfer 10001000 20003000 100");
		assertEquals(aprCalc(1000, 1) + 100, bank.getAccountById("20003000").getBalance());
		assertEquals(aprCalc(1000, 1) - 100, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void transfer_from_checking_to_savings_with_an_amount_greater_than_balance_only_transfers_the_accounts_balance() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 100);
		transferProcessor.process("transfer 20003000 20002000 200");
		assertEquals(0, bank.getAccountById("20003000").getBalance());
		assertEquals(1100, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void transfer_from_checking_to_checking_with_an_amount_greater_than_balance_only_transfers_the_accounts_balance() {
		Checking checking2 = new Checking("20003000", 1);
		bank.addAccount(checking2.getAccountId(), checking2);
		bank.deposit("20003000", 100);
		transferProcessor.process("transfer 20003000 10001000 200");
		assertEquals(0, bank.getAccountById("20003000").getBalance());
		assertEquals(1100, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void transfer_from_savings_to_checking_with_an_amount_greater_than_balance_only_transfers_the_accounts_balance() {
		Savings savings2 = new Savings("20003000", 1);
		bank.addAccount(savings2.getAccountId(), savings2);
		bank.deposit("20003000", 100);
		transferProcessor.process("transfer 20003000 10001000 200");
		assertEquals(0, bank.getAccountById("20003000").getBalance());
		assertEquals(1100, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void transfer_from_savings_to_savings_with_an_amount_greater_than_balance_only_transfers_the_accounts_balance() {
		Savings savings2 = new Savings("20003000", 1);
		bank.addAccount(savings2.getAccountId(), savings2);
		bank.deposit("20003000", 100);
		transferProcessor.process("transfer 20003000 20002000 200");
		assertEquals(0, bank.getAccountById("20003000").getBalance());
		assertEquals(1100, bank.getAccountById("20002000").getBalance());
	}

}
