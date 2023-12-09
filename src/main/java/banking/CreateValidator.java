package banking;

public class CreateValidator extends CommandValidator {

	public CreateValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		if (parsedString[0].equals("create")) {
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
		return (parsedString.length <= 5);
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
