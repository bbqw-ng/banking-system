package banking;

public class DepositValidator extends CommandValidator {

	public DepositValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		if (checkDeposit(parsedString)) {
			if (checkValidId(parsedString)) {
				if (amountChecker(parsedString)) {
					if (super.checkDepositAndWithdrawIdInBank(parsedString)) {
						return (balanceAndAccountChecker(checkAccountTypeFromBank(parsedString), parsedString));
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

	public boolean balanceAndAccountChecker(String accType, String[] parsedString) {
		Double convertAmount = Double.parseDouble(parsedString[2]);
		if (accType.equals("savings")) {
			return (convertAmount >= 0 && convertAmount <= 2500);
		}
		if (accType.equals("checking")) {
			return (convertAmount >= 0 && convertAmount <= 1000);
		}
		if (accType.equals("cd")) {
			return false;
		}
		return false;
	}

}
