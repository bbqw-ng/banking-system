package banking;

public class CreateValidator extends CommandValidator {

	public CreateValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		if (parsedString[0].equals("create") && checkClass(parsedString)) {
			switch (getClass(parsedString)) {
			case ("checking"):
			case ("savings"):
				return validateCheckingOrSavings(parsedString);
			case ("cd"):
				return validateCd(parsedString);
			default:
				break;
			}
		}
		return false;
	}

	private boolean validateCheckingOrSavings(String[] parsedString) {
		return (super.checkValidId(parsedString, 2) && super.checkValidApr(parsedString)
				&& super.checkExtraParameter(parsedString, 4) && super.checkIdInBank(parsedString));
	}

	private boolean validateCd(String[] parsedString) {
		return (super.checkValidId(parsedString, 2) && super.checkValidApr(parsedString) && checkBalance(parsedString)
				&& checkExtraParameter(parsedString, 5) && super.checkIdInBank(parsedString));
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
