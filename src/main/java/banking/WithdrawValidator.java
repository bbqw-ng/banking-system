package banking;

public class WithdrawValidator extends CommandValidator {

	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		// put and instead of thousand ifs :)
		if (checkWithdraw(parsedString)) {
			if (checkValidId(parsedString)) {
				if (amountChecker(parsedString)) {
					if (super.checkDepositAndWithdrawIdInBank(parsedString)) {
						if (bank.getAccountById(parsedString[1]).getAllowWithdraw()) {
							return (validateWithdrawAmount(parsedString));
						}
					}
				}
			}
		}
		return false;
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

	public boolean validateWithdrawAmount(String[] string) {
		BankAccount referenceId = bank.getAccountById(string[1]);
		double amnt = Double.parseDouble(string[2]);
		return referenceId.validWithdrawAmount(amnt);
	}
}
