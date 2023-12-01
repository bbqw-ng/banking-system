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
						return (validateWithdrawAmount(parsedString));
					}
				}
			}
		}
		return false;
	}

	// note We just finished the validate function now we have to establish some
	// tests to check and validate each portion
	// we did not implement the pass time function into this so as of right now we
	// did not do anything about the withdraw limit per months
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

	public boolean balanceAndAccountChecker(String accType, String[] parsedString) {
		Double convertAmount = Double.parseDouble(parsedString[2]);
		if (accType.equals("savings")) {
			return (convertAmount >= 0 && convertAmount <= 1000);
		}
		if (accType.equals("checking")) {
			return (convertAmount >= 0 && convertAmount <= 400);
		}
		if (accType.equals("cd")) {
			return false;
		}
		return false;
	}

	public boolean validateWithdrawAmount(String[] string) {
		BankAccount referenceId = bank.getAccountById(string[1]);
		double amnt = Double.parseDouble(string[2]);
		return referenceId.validWithdrawAmount(amnt);
	}
}
