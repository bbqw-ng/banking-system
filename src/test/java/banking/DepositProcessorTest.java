package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;
	Checking checking;
	Savings savings;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void can_deposit_into_checking_account() {
		checking = new Checking("12345678", 9);
		bank.addAccount(checking.getAccountId(), checking);

		commandProcessor.process("deposit 12345678 500");
		assertEquals(bank.getAccountById("12345678").getBalance(), 500);
	}

	@Test
	public void can_deposit_into_savings_account() {
		savings = new Savings("12345678", 9);
		bank.addAccount(savings.getAccountId(), savings);

		commandProcessor.process("deposit 12345678 1000");
		assertEquals(bank.getAccountById("12345678").getBalance(), 1000);
	}

	@Test
	public void can_deposit_into_checking_account_with_existing_balance() {
		checking = new Checking("12345678", 9);
		checking.doDeposit(1000);
		bank.addAccount(checking.getAccountId(), checking);

		commandProcessor.process("deposit 12345678 500");
		assertEquals(bank.getAccountById("12345678").getBalance(), 1500);
	}

	@Test
	public void can_deposit_into_savings_account_with_existing_balance() {
		savings = new Savings("12345678", 9);
		savings.doDeposit(2000);
		bank.addAccount(savings.getAccountId(), savings);

		commandProcessor.process("deposit 12345678 1000");
		assertEquals(bank.getAccountById("12345678").getBalance(), 3000);
	}
}
