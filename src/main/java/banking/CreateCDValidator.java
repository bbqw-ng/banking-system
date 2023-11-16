package banking;

public class CreateCDValidator extends CommandValidator {
	public static final int MINIMUM_BALANCE_TO_ADD = 1000;
	public static final int MAXIMUM_BALANCE_TO_ADD = 10000;
	public static final int BALANCE = 4;

	public CreateCDValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] array) {

		if (super.checkValidId(array)) {
			if (super.checkValidApr(array)) {
				if (checkBalance(array)) {
					if (checkExtraParameter(array)) {
						return (super.checkIdInBank(array));
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkExtraParameter(String[] string) {
		try {
			String test = string[5];
			return false;
		} catch (Exception exceptionOne) {
			return true;
		}
	}

	public boolean checkBalance(String[] string) {
		try {
			double convertBalance = Double.parseDouble(string[BALANCE]);
			return (convertBalance >= MINIMUM_BALANCE_TO_ADD && convertBalance <= MAXIMUM_BALANCE_TO_ADD);
		} catch (Exception exception) {
			return false;
		}
	}

}
