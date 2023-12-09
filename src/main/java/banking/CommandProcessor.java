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
		String lowerCased = command.toLowerCase();
		String[] parsedString = parseString(lowerCased);
		switch (parsedString[0]) {
		case ("create"):
			startCreateProcessor(bank, parsedString);
			break;
		case ("deposit"):
			startDepositProcessor(bank, parsedString, command);
			break;
		case ("withdraw"):
			startWithdrawProcessor(bank, parsedString, command);
			break;
		case ("transfer"):
			startTransferProcessor(bank, parsedString, command);
			break;
		case ("pass"):
			startPassTimeProcessor(bank, parsedString);
			break;
		default:
			break;
		}
	}

	public String[] parseString(String command) {
		return command.toLowerCase().split(" ");
	}

	private void startCreateProcessor(Bank bank, String[] parsedString) {
		createProcessor = new CreateProcessor(bank);
		createProcessor.process(parsedString);
	}

	private void startDepositProcessor(Bank bank, String[] parsedString, String command) {
		depositProcessor = new DepositProcessor(bank);
		bank.getAccountById(parsedString[1]).addAssociatedCommand(command);
		depositProcessor.process(parsedString);
	}

	private void startWithdrawProcessor(Bank bank, String[] parsedString, String command) {
		withdrawProcessor = new WithdrawProcessor(bank);
		bank.getAccountById(parsedString[1]).addAssociatedCommand(command);
		withdrawProcessor.process(parsedString);
	}

	private void startTransferProcessor(Bank bank, String[] parsedString, String command) {
		transferProcessor = new TransferProcessor(bank);
		bank.getAccountById(parsedString[1]).addAssociatedCommand(command);
		bank.getAccountById(parsedString[2]).addAssociatedCommand(command);
		transferProcessor.process(parsedString);
	}

	private void startPassTimeProcessor(Bank bank, String[] parsedString) {
		passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.process(parsedString);
	}

}
