package banking;

public class DepositValidator extends CommandValidator {

	public DepositValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		return (parsedString[0].equals("deposit") && checkValidId(parsedString) && amountChecker(parsedString)
				&& super.checkDepositAndWithdrawIdInBank(parsedString) && validateDepositAmount(parsedString));
	}

	@Override
	public boolean checkValidId(String[] parsedString) {
		try {
			int strToInt = Integer.parseInt(parsedString[1]);
			return (parsedString[1].length() == 8 && strToInt >= 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean amountChecker(String[] string) {
		try {
			double convertAmount = Double.parseDouble(string[2]);
			if (convertAmount >= 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateDepositAmount(String[] string) {
		BankAccount referenceId = bank.getAccountById(string[1]);
		double amnt = Double.parseDouble(string[2]);
		return referenceId.validDepositAmount(amnt);
	}

}
