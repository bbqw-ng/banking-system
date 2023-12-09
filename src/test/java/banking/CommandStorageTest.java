package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {

	Bank bank;
	CommandStorage commandStorage;
	String invalidCreateCommand;
	String invalidDepositCommand;
	Checking checking;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
		checking = new Checking("10001000", 1);
		invalidCreateCommand = "creat 111 10032000 8";
		invalidDepositCommand = "depoose 12 1";
	}

	@Test
	public void has_no_command_at_first() {
		assertEquals(commandStorage.getInvalidCommands().size(), 0);

	}

	@Test
	public void store_an_invalid_create_command() {
		commandStorage.storeInvalidCommand(invalidCreateCommand);
		assertEquals(commandStorage.getInvalidCommands().size(), 1);
		assertEquals(commandStorage.getInvalidCommands().get(0), invalidCreateCommand);
	}

	@Test
	public void store_two_invalid_create_commands() {
		String invalidTwo = "creat1 833 7";
		commandStorage.storeInvalidCommand(invalidCreateCommand);
		commandStorage.storeInvalidCommand(invalidTwo);
		assertEquals(commandStorage.getInvalidCommands().size(), 2);
		assertEquals(commandStorage.getInvalidCommands().get(0), invalidCreateCommand);
		assertEquals(commandStorage.getInvalidCommands().get(1), "creat1 833 7");
	}

	@Test
	public void store_an_invalid_deposit_command() {
		commandStorage.storeInvalidCommand(invalidDepositCommand);
		assertEquals(commandStorage.getInvalidCommands().size(), 1);
		assertEquals(commandStorage.getInvalidCommands().get(0), invalidDepositCommand);
	}

	@Test
	public void store_two_invalid_deposit_command() {
		String invalidTwo = "deppos 00100 29";
		commandStorage.storeInvalidCommand(invalidDepositCommand);
		commandStorage.storeInvalidCommand(invalidTwo);
		assertEquals(commandStorage.getInvalidCommands().size(), 2);
		assertEquals(commandStorage.getInvalidCommands().get(0), invalidDepositCommand);
		assertEquals(commandStorage.getInvalidCommands().get(1), "deppos 00100 29");
	}

	@Test
	public void store_all_commands() {
		bank.addAccount(checking.getAccountId(), checking);
		commandStorage.storeInvalidCommand(invalidDepositCommand);
		commandStorage.addAllCommands();
		assertEquals(commandStorage.getAllCommands().size(), 2);
	}
}
