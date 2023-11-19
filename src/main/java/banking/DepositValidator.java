package banking;

public class DepositValidator extends CommandValidator {

	public DepositValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] array) {
		if (checkValidId(array)) {
			if (amountChecker(array)) {
				if (super.checkIdInBank(array)) {
					return (balanceAndAccountChecker(checkAccountTypeFromBank(array), array));
				}
			}
		}
		return false;

	}

	@Override
	public boolean checkValidId(String[] string) {
		try {
			int strToInt = Integer.parseInt(string[1]);
			return (string[1].length() == 8 && strToInt >= 0);
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

	public boolean balanceAndAccountChecker(String string, String[] array) {
		Double convertAmount = Double.parseDouble(array[2]);
		if (string.equals("savings")) {
			return (convertAmount >= 0 && convertAmount <= 2500);
		}
		if (string.equals("checking")) {
			return (convertAmount >= 0 && convertAmount <= 1000);
		}
		if (string.equals("cd")) {
			return false;
		}
		return false;
	}

}
