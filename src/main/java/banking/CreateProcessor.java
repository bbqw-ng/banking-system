package banking;

public class CreateProcessor extends CommandProcessor {
	private Checking checking;
	private Savings savings;
	private CD cd;

	public CreateProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsedString) {
		switch (parsedString[1]) {
		case ("checking"):
		case ("savings"):
			processCheckingOrSavings(parsedString);
			break;
		case ("cd"):
			processCD(parsedString);
			break;
		default:
			break;
		}
	}

	private void processCheckingOrSavings(String[] parsed) {
		String id = parsed[2];
		double apr = Double.parseDouble(parsed[3]);
		if (parsed[1].equals("checking")) {
			checking = new Checking(id, apr);
			bank.addAccount(checking.getAccountId(), checking);
		} else {
			savings = new Savings(id, apr);
			bank.addAccount(savings.getAccountId(), savings);
		}
	}

	private void processCD(String[] parsed) {
		String id = parsed[2];
		double apr = Double.parseDouble(parsed[3]);
		double balance = Double.parseDouble(parsed[4]);
		cd = new CD(id, apr, balance);
		bank.addAccount(cd.getAccountId(), cd);
	}
}
