package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawProcessorTest {
	Bank bank;
	WithdrawProcessor withdrawProcessor;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		withdrawProcessor = new WithdrawProcessor(bank);
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
	public void can_withdraw_from_checking_with_balance_of_1000() {
		bank.deposit("10001000", 1000);
		withdrawProcessor.process("withdraw 10001000 200");
		assertEquals(800, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void can_withdraw_from_checking_with_balance_of_0() {
		withdrawProcessor.process("withdraw 10001000 200");
		assertEquals(0, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void can_withdraw_from_savings_with_balance_of_1000_after_a_month_passes() {
		bank.deposit("20002000", 1000);
		double savingsBalance = aprCalc(1000, 5);
		bank.pass(1);
		withdrawProcessor.process("withdraw 20002000 200");
		assertEquals(savingsBalance - 200, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void can_withdraw_from_savings_account_with_balance_of_0() {
		withdrawProcessor.process("withdraw 20002000 200");
		assertEquals(0, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void can_withdraw_from_cd_after_12_months_pass_and_amount_equals_balance() {
		double cdBalance = aprCalcCD(1000, 5);
		bank.pass(1);
		withdrawProcessor.process("withdraw 30003000 1000");
		assertEquals(0, bank.getAccountById("30003000").getBalance() - cdBalance);
	}

	@Test
	public void can_withdraw_twice_from_checking_account_in_the_same_month() {
		bank.deposit("10001000", 1000);
		withdrawProcessor.process("withdraw 10001000 100");
		withdrawProcessor.process("withdraw 10001000 100");
		assertEquals(800, bank.getAccountById("10001000").getBalance());
	}

	@Test
	public void can_only_withdraw_once_from_savings_account_in_a_month() {
		bank.deposit("20002000", 1000);
		double savingsBalance = aprCalc(1000, 5);
		bank.pass(1);
		withdrawProcessor.process("withdraw 20002000 200");
		withdrawProcessor.process("withdraw 20002000 200");
		assertEquals(savingsBalance - 200, bank.getAccountById("20002000").getBalance());
	}

	@Test
	public void can_withdraw_once_from_cd_account_after_12_months() {
		bank.pass(12);
		withdrawProcessor.process("withdraw 30003000 5000");
		assertEquals(0, bank.getAccountById("30003000").getBalance());
	}
}
