package banking;

public class TransferValidator extends CommandValidator {

	public TransferValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		if (parsedString[0].equals("transfer") && checkValidSenderId(parsedString) && checkValidReceiverId(parsedString)
				&& checkAmount(parsedString)) {
			if (checkIdInBank(parsedString, 1) && checkIdInBank(parsedString, 2)) {
				if (!(cdAccountChecker(parsedString, 1) || cdAccountChecker(parsedString, 2))) {
					if (bank.getAccountById(parsedString[1]).getAllowWithdraw()) {
						return (bank.getAccountById(parsedString[1])
								.validWithdrawAmount(Double.parseDouble(parsedString[3]))
								&& bank.getAccountById(parsedString[1])
										.validDepositAmount(Double.parseDouble(parsedString[3])));
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

	public boolean checkIdInBank(String[] string, int index) {
		try {
			if (bank.getAccountById(string[index]) != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean cdAccountChecker(String[] parsedString, int index) {
		return (bank.getAccountById(parsedString[index]).getAccountType().equals("cd"));
	}

}