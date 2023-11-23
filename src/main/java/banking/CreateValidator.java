package banking;

public class CreateValidator extends CommandValidator {

	public CreateValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		if (checkCreate(parsedString)) {
			if (checkClass(parsedString)) {
				switch (getClass(parsedString)) {

				case ("checking"):
				case ("savings"):
					if (super.checkValidId(parsedString)) {
						if (super.checkValidApr(parsedString)) {
							if (super.checkExtraParameter(parsedString)) {
								return (super.checkIdInBank(parsedString));
							}
						}
					}
					return false;

				case ("cd"):
					if (super.checkValidId(parsedString)) {
						if (super.checkValidApr(parsedString)) {
							if (checkBalance(parsedString)) {
								if (checkExtraParameter(parsedString)) {
									return (super.checkIdInBank(parsedString));
								}
							}
						}
					}
					return false;

				default:
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkExtraParameter(String[] parsedString) {
		try {
			String testToSeeIfExtraParamForCdAccount = parsedString[5];
			return false;
		} catch (Exception exceptionOne) {
			return true;
		}
	}

	public boolean checkBalance(String[] parsedString) {
		try {
			double convertBalance = Double.parseDouble(parsedString[4]);
			return (convertBalance >= 1000 && convertBalance <= 10000);
		} catch (Exception exception) {
			return false;
		}
	}
}
