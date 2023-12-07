package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {
	Bank bank;
	PassTimeProcessor passTimeProcessor;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		passTimeProcessor = new PassTimeProcessor(bank);
		checking = new Checking("10001000", 5);
		savings = new Savings("20002000", 5);
		cd = new CD("30003000", 5, 1000);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.addAccount(cd.getAccountId(), cd);
	}

	private double aprCalc(double balance, double apr) {
		double aprConvertToPercentage = apr / 100;
		double monthlyAprConvert = aprConvertToPercentage / 12;
		double monthlyBalanceGain = balance * monthlyAprConvert;
		return monthlyBalanceGain + balance;
	}

	private double aprCalcCD(double balance, double apr) {
		double total = balance;
		double aprConvertToPercentage = apr / 100;
		double monthlyAprConvert = aprConvertToPercentage / 12;
		for (int reps = 0; reps < 4; reps++) {
			double monthlyBalanceGain = total * monthlyAprConvert;
			total += monthlyBalanceGain;
		}
		return total;
	}

	@Test
	public void pass_1_closes_checking_account_with_0_balance() {
		bank.deposit("20002000", 100);
		passTimeProcessor.process("passtime 1");
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void pass_1_closes_savings_account_with_0_balance() {
		bank.deposit("10001000", 100);
		passTimeProcessor.process("passtime 1");
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void pass_1_closes_cd_account_with_0_balance() {
		bank.deposit("10001000", 100);
		bank.deposit("20002000", 100);
		bank.withdraw("30003000", 1000);
		passTimeProcessor.process("passtime 1");
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void pass_1_closes_checking_and_savings_with_0_balance() {
		passTimeProcessor.process("passtime 1");
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	public void pass_1_closes_all_types_of_accounts_with_0_balance() {
		bank.withdraw("30003000", 1000);
		passTimeProcessor.process("passtime 1");
		assertEquals(0, bank.getAccounts().size());
	}

	@Test
	public void pass_1_deducts_checking_account_with_less_than_100_balance() {
		bank.deposit("10001000", 75);
		double checkingBalance = aprCalc(bank.getAccountById("10001000").getBalance() - 25, 5);

		passTimeProcessor.process("passtime 1");
		assertEquals(checkingBalance, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void pass_1_deducts_savings_account_with_less_than_100_balance() {
		bank.deposit("20002000", 50);
		double savingsBalance = aprCalc(bank.getAccountById("20002000").getBalance() - 25, 5);

		passTimeProcessor.process("passtime 1");
		assertEquals(savingsBalance, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void pass_1_accrues_apr_correctly_for_checking_with_100_balance() {
		bank.deposit("10001000", 100);
		double checkingBalance = aprCalc(bank.getAccountById("10001000").getBalance(), 5);

		passTimeProcessor.process("passtime 1");
		assertEquals(checkingBalance, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void pass_1_accrues_apr_correctly_for_savings_with_100_balance() {
		bank.deposit("20002000", 100);
		double savingsBalance = aprCalc(bank.getAccountById("20002000").getBalance(), 5);

		passTimeProcessor.process("passtime 1");
		assertEquals(savingsBalance, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void pass_1_accrues_apr_correctly_for_cd_with_1000_balance() {
		double cdBalance = aprCalcCD(bank.getAccountById("30003000").getBalance(), 5);

		passTimeProcessor.process("passtime 1");
		assertEquals(cdBalance, bank.getAccountById("30003000").getBalance());
	}

	@Test
	public void pass_60_closes_a_checking_account_with_0_balance() {
		bank.deposit("20002000", 100);

		passTimeProcessor.process("passtime 60");
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void pass_60_closes_a_savings_account_with_0_balance() {
		bank.deposit("10001000", 100);
		passTimeProcessor.process("passtime 60");

		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void pass_60_closes_both_savings_and_checking_with_0_balance() {
		passTimeProcessor.process("passtime 60");
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	public void pass_60_closes_all_account_types_with_0_balance() {
		bank.withdraw("30003000", 1000);
		passTimeProcessor.process("passtime 60");
		assertEquals(0, bank.getAccounts().size());
	}

	@Test
	public void pass_2_deducts_a_checking_account_with_less_than_100_balance_correctly() {
		bank.deposit("10001000", 100);
		double monthOne = aprCalc(bank.getAccountById("10001000").getBalance(), 5);
		double monthTwo = aprCalc(monthOne, 5);
		passTimeProcessor.process("passtime 2");
		assertEquals(monthTwo, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void pass_2_deducts_a_savings_account_with_less_than_100_balance_correctly() {
		bank.deposit("20002000", 100);
		double monthOne = aprCalc(bank.getAccountById("20002000").getBalance(), 5);
		double monthTwo = aprCalc(monthOne, 5);
		passTimeProcessor.process("passtime 2");
		assertEquals(monthTwo, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void pass_2_accrues_a_checking_account_with_5_apr_correctly() {
		bank.deposit("10001000", 100);
		double monthOne = aprCalc(bank.getAccountById("10001000").getBalance(), 5);
		double monthTwo = aprCalc(monthOne, 5);
		passTimeProcessor.process("passtime 2");
		assertEquals(monthTwo, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void pass_2_accrues_a_savings_account_with_5_apr_correctly() {
		bank.deposit("20002000", 100);
		double monthOne = aprCalc(bank.getAccountById("20002000").getBalance(), 5);
		double monthTwo = aprCalc(monthOne, 5);
		passTimeProcessor.process("passtime 2");
		assertEquals(monthTwo, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void pass_2_accrues_a_cd_account_with_5_apr_correctly() {
		double monthOne = aprCalcCD(bank.getAccountById("30003000").getBalance(), 5);
		double monthTwo = aprCalcCD(monthOne, 5);
		passTimeProcessor.process("passtime 2");
		assertEquals(monthTwo, bank.getAccountById("30003000").getBalance());
	}

	@Test
	public void passing_1_twice_accrues_a_savings_account_5_apr_correctly() {
		bank.deposit("10001000", 1000);
		double monthOne = aprCalc(1000, 5);
		double monthTwo = aprCalc(monthOne, 5);
		passTimeProcessor.process("passtime 1");
		passTimeProcessor.process("passtime 1");
		assertEquals(monthTwo, bank.getAccountById("10001000").getBalance());
	}

}