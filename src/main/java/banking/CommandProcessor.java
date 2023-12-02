package banking;

public class CommandProcessor {
	protected Bank bank;
	private CreateProcessor createProcessor;
	private DepositProcessor depositProcessor;
	private PassTimeProcessor passTimeProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] parsedString = parseString(command);
		if (parsedString[0].equals("create")) {
			createProcessor = new CreateProcessor(bank);
			createProcessor.process(parsedString);
		} else if (parsedString[0].equals("deposit")) {
			depositProcessor = new DepositProcessor(bank);
			depositProcessor.process(parsedString);
		} else if (parsedString[0].equals("passtime")) {
			passTimeProcessor = new PassTimeProcessor(bank);
			passTimeProcessor.process(parsedString);
		}
	}

	public String[] parseString(String command) {
		return command.toLowerCase().split(" ");
	}

}
