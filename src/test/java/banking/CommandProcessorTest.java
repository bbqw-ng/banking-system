package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {

	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
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

}
