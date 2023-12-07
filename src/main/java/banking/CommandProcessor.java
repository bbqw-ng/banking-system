package banking;

public class CommandProcessor {
	protected Bank bank;
	private CreateProcessor createProcessor;
	private DepositProcessor depositProcessor;
	private PassTimeProcessor passTimeProcessor;
	private WithdrawProcessor withdrawProcessor;
	private TransferProcessor transferProcessor;

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
		} else if (parsedString[0].equals("pass")) {
			passTimeProcessor = new PassTimeProcessor(bank);
			passTimeProcessor.process(parsedString);
		} else if (parsedString[0].equals("withdraw")) {
			withdrawProcessor = new WithdrawProcessor(bank);
			withdrawProcessor.process(parsedString);
		} else if (parsedString[0].equals("transfer")) {
			transferProcessor = new TransferProcessor(bank);
			transferProcessor.process(parsedString);
		}
	}

	public String[] parseString(String command) {
		return command.toLowerCase().split(" ");
	}

}
