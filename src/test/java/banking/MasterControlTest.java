package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List<String> input;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
		input = new ArrayList<>();

	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	public void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	public void typo_in_deposit_command_is_invalid() {
		input.add("depossit 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depossit 12345678 100", actual);
	}

	@Test
	public void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depossit 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depossit 12345678 100", actual.get(1));
	}

	@Test
	public void invalid_to_create_accounts_with_same_id() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create checking 12345678 1.0", actual);
	}

	@Test
	public void can_retrieve_commands_associated_to_account() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 100");
		input.add("withdraw 10001000 50");

		List<String> actual = masterControl.start(input);
	}

	// how would i get it so I am able to loop through the linked hash map, for each
	// account display the account balance and the command
	// used on it?
}
