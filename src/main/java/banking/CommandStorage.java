package banking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	public List<String> getAllCommands() {
		return allCommands;
	}

	public String getInvalidCommandWithIndex(int index) {
		return invalidCommands.get(index);
	}

	public void addAllCommands() {
		Iterator<Map.Entry<String, BankAccount>> iterator = bank.getAccounts().entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, BankAccount> entry = iterator.next();
			BankAccount account = entry.getValue();
			storeCommand(account.getAccountStatus());
			for (int command = 0; command < account.getAssociatedCommands().size(); command++) {
				storeCommand(account.getAssociatedCommands().get(command));
			}
		}
		allCommands.addAll(invalidCommands);
	}
}
