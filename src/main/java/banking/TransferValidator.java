package banking;

public class TransferValidator extends CommandValidator {

	public TransferValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		if (super.checkTransfer(parsedString)) {
			if (checkValidSenderId(parsedString)) {
				if (checkValidReceiverId(parsedString)) {
					if (checkAmount(parsedString)) {
						if (!(bank.getAccountById(parsedString[1]).getAccountType().equals("cd")
								|| bank.getAccountById(parsedString[2]).getAccountType().equals("cd"))) {
							return (bank.getAccountById(parsedString[1])
									.validWithdrawAmount(Double.parseDouble(parsedString[3]))
									&& bank.getAccountById(parsedString[1])
											.validDepositAmount(Double.parseDouble(parsedString[3])));
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkValidSenderId(String[] parsedString) {
		try {
			int strToInt = Integer.parseInt(parsedString[1]);
			return (parsedString[1].length() == 8 && strToInt >= 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkValidReceiverId(String[] parsedString) {
		try {
			int strToInt = Integer.parseInt(parsedString[2]);
			return (parsedString[2].length() == 8 && strToInt >= 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkAmount(String[] parsedString) {
		try {
			double amount = Double.parseDouble(parsedString[3]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getSenderAccountType(String[] parsedString) {
		switch (super.getBank().getAccountById(parsedString[1]).getAccountType()) {
		case "checking":
			return "checking";
		case "savings":
			return "savings";
		case "cd":
			return "cd";
		default:
			return null;
		}
	}

	// Checking Withdraw Rules
	public boolean checkCheckingSenderAmount(String[] parsedString) {
		try {
			double amount = Double.parseDouble(parsedString[3]);
			return (amount >= 0 && amount <= 400);
		} catch (Exception e) {
			return false;
		}
	}

	// Savings Withdraw Rules (need to implement pass time on this)
	// can only withdraw once per month (transfer counts as withdraw).
	public boolean checkSavingsSenderAmount(String[] parsedString) {
		try {
			double amount = Double.parseDouble(parsedString[3]);
			return (amount >= 0 && amount <= 1000);
		} catch (Exception e) {
			return false;
		}
	}

	public String getReceiverAccountType(String[] parsedString) {
		switch (super.getBank().getAccountById(parsedString[2]).getAccountType()) {
		case "checking":
			return "checking";
		case "savings":
			return "savings";
		case "cd":
			return "cd";
		default:
			return null;
		}
	}

	public boolean checkCheckingReceiverAmount(String[] parsedString) {
		try {
			double amount = Double.parseDouble(parsedString[3]);
			return (amount >= 0 && amount <= 1000);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkSavingsReceiverAmount(String[] parsedString) {
		try {
			double amount = Double.parseDouble(parsedString[3]);
			return (amount >= 0 && amount <= 2500);
		} catch (Exception e) {
			return false;
		}
	}
	// need to implement pass time funciton for savings withdrawal in this (once per
	// month) passtime(1).
}
