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
					return (super.checkValidId(parsedString) && super.checkValidApr(parsedString)
							&& super.checkExtraParameter(parsedString) && super.checkIdInBank(parsedString));
				case ("cd"):
					return (super.checkValidId(parsedString) && super.checkValidApr(parsedString)
							&& checkBalance(parsedString) && checkExtraParameter(parsedString)
							&& super.checkIdInBank(parsedString));
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
