package banking;

public class DepositProcessor extends CommandProcessor {

	public DepositProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsed) {
		String id = parsed[1];
		double amount = Double.parseDouble(parsed[2]);
		bank.getAccountById(id).doDeposit(amount);
	}

}
