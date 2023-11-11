public class CommandProcessor {
	private Bank bank;
	private Checking checking;
	private Savings savings;
	private CD cd;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] parsedString = parseString(command);
		if (parsedString[0].equals("create")) {
			switch (parsedString[1]) {
			case ("checking"):
				processChecking(parsedString);
				break;
			case ("savings"):
				processSavings(parsedString);
				break;
			case ("cd"):
				processCD(parsedString);
				break;
			}
		} else if (parsedString[0].equals("deposit")) {
			processDeposit(parsedString);
		}
	}

	public String[] parseString(String command) {
		return command.toLowerCase().split(" ");
	}

	public void processChecking(String[] parsed) {
		String id = parsed[2];
		double apr = Double.parseDouble(parsed[3]);
		checking = new Checking(id, apr);
		bank.addAccount(checking.getAccountId(), checking);
	}

	public void processCD(String[] parsed) {
		String id = parsed[2];
		double apr = Double.parseDouble(parsed[3]);
		double balance = Double.parseDouble(parsed[4]);
		cd = new CD(id, apr, balance);
		bank.addAccount(cd.getAccountId(), cd);
	}

	public void processSavings(String[] parsed) {
		String id = parsed[2];
		double apr = Double.parseDouble(parsed[3]);
		savings = new Savings(id, apr);
		bank.addAccount(savings.getAccountId(), savings);
	}

	public void processDeposit(String[] parsed) {
		String id = parsed[1];
		double amount = Double.parseDouble(parsed[2]);
		bank.getAccountById(id).doDeposit(amount);
	}
}
