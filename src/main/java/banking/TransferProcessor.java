package banking;

public class TransferProcessor extends CommandProcessor {

	public TransferProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsedString) {
		double amount = Double.parseDouble(parsedString[3]);
		String senderId = parsedString[1];
		String receiverId = parsedString[2];
		if (bank.getAccountById(senderId).getAllowWithdraw() == true) {
			if (amount > bank.getAccountById(senderId).getBalance()) {
				bank.deposit(receiverId, bank.getAccountById(senderId).getBalance());
			} else {
				bank.deposit(receiverId, amount);
			}
			bank.withdraw(parsedString[1], amount);

			if (bank.getAccountById(senderId).getAccountType().equals("savings")) {
				bank.getAccountById(senderId).canWithdraw(false);
			}
		}
		bank.getAccountById(senderId).addAssociatedCommand(String.join(" ", parsedString));
		bank.getAccountById(receiverId).addAssociatedCommand(String.join(" ", parsedString));
	}
}
