package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {

	Bank bank;
	CommandProcessor commandProcessor;
	Checking checking;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void processor_can_parse_string() {
		String[] parsedString = commandProcessor.parseString("create checking 10002000 10");
		assertEquals("create", parsedString[0]);
		assertEquals("checking", parsedString[1]);
		assertEquals("10002000", parsedString[2]);
		assertEquals("10", parsedString[3]);
	}

	@Test
	public void processor_can_process_a_checking_command() {
		commandProcessor.process("create checking 10002000 10");
		assertEquals(bank.getAccountById("10002000").getAccountType(), "checking");
		assertEquals(bank.getAccountById("10002000").getAccountId(), "10002000");
	}

	@Test
	public void processor_can_process_a_deposit_command() {
		Checking checking = new Checking("11112222", 2);
		bank.addAccount(checking.getAccountId(), checking);
		commandProcessor.process("deposit 11112222 40");

		assertEquals(bank.getAccountById("11112222").getBalance(), 40);
	}

}
