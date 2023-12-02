package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateProcessorTest {

	Bank bank;
	CreateProcessor createProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createProcessor = new CreateProcessor(bank);
	}

	@Test
	public void can_create_a_checking_account_with_correct_id_and_apr() {
		createProcessor.process("create checking 12345678 1.0");
		assertEquals(bank.getAccountById("12345678").getAccountType(), "checking");
		assertEquals(bank.getAccountById("12345678").getAccountId(), "12345678");
		assertEquals(bank.getAccountById("12345678").getApr(), 1);
	}

	@Test
	public void can_create_a_savings_account_with_correct_id_and_apr() {
		createProcessor.process("create savings 12345678 1.0");
		assertEquals(bank.getAccountById("12345678").getAccountType(), "savings");
		assertEquals(bank.getAccountById("12345678").getAccountId(), "12345678");
		assertEquals(bank.getAccountById("12345678").getApr(), 1);
	}

	@Test
	public void can_create_a_cd_account_with_correct_id_and_apr() {
		createProcessor.process("create cd 12345678 1.0 5000");
		assertEquals(bank.getAccountById("12345678").getAccountType(), "cd");
		assertEquals(bank.getAccountById("12345678").getAccountId(), "12345678");
		assertEquals(bank.getAccountById("12345678").getApr(), 1);
		assertEquals(bank.getAccountById("12345678").getBalance(), 5000);
	}

	@Test
	public void can_create_two_accounts_with_correct_id_and_apr() {
		createProcessor.process("create checking 12345678 1.0");
		assertEquals(bank.getAccountById("12345678").getAccountType(), "checking");
		assertEquals(bank.getAccountById("12345678").getAccountId(), "12345678");
		assertEquals(bank.getAccountById("12345678").getApr(), 1);

		createProcessor.process("create cd 22345678 1.0 5000");
		assertEquals(bank.getAccountById("22345678").getAccountType(), "cd");
		assertEquals(bank.getAccountById("22345678").getAccountId(), "22345678");
		assertEquals(bank.getAccountById("22345678").getApr(), 1);
		assertEquals(bank.getAccountById("22345678").getBalance(), 5000);
	}
}
