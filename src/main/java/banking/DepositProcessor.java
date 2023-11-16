package banking;

public class DepositProcessor extends CommandProcessor {

	private Bank bank;

	public DepositProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void process(String[] parsed) {
		String id = parsed[1];
		double amount = Double.parseDouble(parsed[2]);
		bank.getAccountById(id).doDeposit(amount);
	}

}
