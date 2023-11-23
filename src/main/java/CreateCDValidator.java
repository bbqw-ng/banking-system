public class CreateCDValidator extends CreateValidator {
	public static final int MINIMUM_BALANCE_TO_ADD = 1000;
	public static final int MAXIMUM_BALANCE_TO_ADD = 10000;
	public static final int BALANCE = 4;
	private CD cd;
	private Bank bank;

	public CreateCDValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String lowerCaseCommand = turnLowerCase(command);
		String[] parsedString = stringParser(lowerCaseCommand);

		if (super.checkCreate(parsedString)) {
			if (super.checkClass(parsedString)) {
				if (super.checkValidId(parsedString)) {
					if (super.checkValidApr(parsedString)) {
						if (checkBalance(parsedString)) {
							if (checkExtraParameter(parsedString)) {
								if (super.checkIdInBank(parsedString)) {
									return addAccountIntoBank(parsedString);
								}
							}
						}
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

	@Override
	public boolean addAccountIntoBank(String[] string) {
		double aprToDouble = Double.parseDouble(string[APR]);
		double balanceToDouble = Double.parseDouble(string[CreateCDValidator.BALANCE]);
		cd = new CD(string[ID], aprToDouble, balanceToDouble);
		bank = super.getBank();
		bank.addAccount(string[ID], cd);
		return true;
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
