package banking;

public class TransferProcessor extends CommandProcessor {

	public TransferProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsedString) {
		if (bank.getAccountById(parsedString[1]).getAllowWithdraw() == true) {
			startTransfer(parsedString);
			if (bank.getAccountById(parsedString[1]).getAccountType().equals("savings")) {
				changeSavingsWithdrawAvailability(parsedString);
			}
		}
	}

	private void startTransfer(String[] parsedString) {
		if (Double.parseDouble(parsedString[3]) > bank.getAccountById(parsedString[1]).getBalance()) {
			bank.deposit(parsedString[2], bank.getAccountById(parsedString[1]).getBalance());
		} else {
			bank.deposit(parsedString[2], Double.parseDouble(parsedString[3]));
		}
		bank.withdraw(parsedString[1], Double.parseDouble(parsedString[3]));
	}

	private void changeSavingsWithdrawAvailability(String[] parsedString) {
		bank.getAccountById(parsedString[1]).canWithdraw(false);
	}
}
