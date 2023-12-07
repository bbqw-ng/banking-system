package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private Bank bank;
	private List<String> invalidCommands = new ArrayList<>();
	private List<String> allCommands = new ArrayList<>();

	public CommandStorage(Bank bank) {
		this.bank = bank;
	}

	public void storeInvalidCommand(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public void storeCommand(String command) {
		allCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public String getInvalidCommandWithIndex(int index) {
		return invalidCommands.get(index);
	}

}
