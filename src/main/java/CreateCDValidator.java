public class CreateCDValidator extends CreateValidator {
	private Bank bank;
	private CD cd;

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
									if (addAccountIntoBank(parsedString)) {
										return true;
									}
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
		double aprToDouble = Double.parseDouble(string[3]);
		double balanceToDouble = Double.parseDouble(string[4]);
		cd = new CD(string[2], aprToDouble, balanceToDouble);
		return true;
	}

	public boolean checkBalance(String[] string) {
		try {
			double convertBalance = Double.parseDouble(string[4]);
			return (convertBalance >= 1000 && convertBalance <= 10000);
		} catch (Exception exception) {
			return false;
		}
	}

}
