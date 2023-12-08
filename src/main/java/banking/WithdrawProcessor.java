package banking;

public class WithdrawProcessor extends CommandProcessor {

	public WithdrawProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsedString) {
		double amount = Double.parseDouble(parsedString[2]);
		String id = parsedString[1];
		if (bank.getAccountById(id).getAllowWithdraw() == true) {
			bank.withdraw(parsedString[1], amount);

			if (bank.getAccountById(id).getAccountType().equals("savings")) {
				bank.getAccountById(id).canWithdraw(false);
			} else if (bank.getAccountById(id).getAccountType().equals("cd")) {
				bank.getAccountById(id).canWithdraw(false);
			}
		}
	}
}
