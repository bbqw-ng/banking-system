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
		checking = new Checking("11112222", 9);
		savings = new Savings("22223333", 9);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
	}

	@Test
	public void can_process_a_deposit_command() {
		commandProcessor.process("deposit 22223333 1");
		assertEquals(bank.getAccountById("22223333").getBalance(), 1);
	}

	@Test
	public void can_deposit_into_checking_account() {
		commandProcessor.process("deposit 11112222 500");
		assertEquals(bank.getAccountById("11112222").getBalance(), 500);
	}

	@Test
	public void can_deposit_into_savings_account() {
		commandProcessor.process("deposit 22223333 1000");
		assertEquals(bank.getAccountById("22223333").getBalance(), 1000);
	}

	@Test
	public void can_deposit_into_checking_account_with_existing_balance() {
		bank.deposit("11112222", 1000);

		commandProcessor.process("deposit 11112222 500");
		assertEquals(bank.getAccountById("11112222").getBalance(), 1500);
	}

	@Test
	public void can_deposit_into_savings_account_with_existing_balance() {
		bank.deposit("22223333", 2000);

		commandProcessor.process("deposit 22223333 1000");
		assertEquals(bank.getAccountById("22223333").getBalance(), 3000);
	}
}
