package banking;

public class DepositProcessor extends CommandProcessor {

	public DepositProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsedString) {
		String id = parsedString[1];
		double amount = Double.parseDouble(parsedString[2]);
		bank.getAccountById(id).doDeposit(amount);
	}

}
