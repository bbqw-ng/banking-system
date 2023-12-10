package banking;

public class TransferValidator extends CommandValidator {

	public TransferValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		boolean validTransfer = parsedString[0].equals("transfer") && checkValidSenderId(parsedString)
				&& checkValidReceiverId(parsedString) && checkSenderAndReceiverAccountAreNotTheSame(parsedString)
				&& super.checkExtraParameter(parsedString, 4) && checkAmount(parsedString)
				&& checkForIdsInBank(parsedString) && areAccountsValidForTransfer(parsedString);
		if (validTransfer) {
			return (bank.getAccountById(parsedString[1]).validWithdrawAmount(Double.parseDouble(parsedString[3]))
					&& bank.getAccountById(parsedString[1]).validDepositAmount(Double.parseDouble(parsedString[3])));
		}
		return false;
	}

	private boolean checkForIdsInBank(String[] parsedString) {
		return (checkIdInBank(parsedString, 1) && checkIdInBank(parsedString, 2));
	}

	private boolean areAccountsValidForTransfer(String[] parsedString) {
		return !(cdAccountChecker(parsedString, 1) || cdAccountChecker(parsedString, 2))
				&& bank.getAccountById(parsedString[1]).getAllowWithdraw();
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

	public boolean checkSenderAndReceiverAccountAreNotTheSame(String[] parsedString) {
		return (!(parsedString[1].equals(parsedString[2])));
	}

	public boolean checkAmount(String[] parsedString) {
		try {
			Double.parseDouble(parsedString[3]);
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