import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

	private List<String> invalidCommands = new ArrayList<>();

	public void storeInvalidCommand(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public String getInvalidCommandWithIndex(int index) {
		return invalidCommands.get(index);
	}
}
