public class CommandProcessor {
	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] parsedString = parseString(command);
		switch (parsedString[0]) {
		case "create":

		}

	}

	public String[] parseString(String command) {
		return command.toLowerCase().split(" ");
	}

}
