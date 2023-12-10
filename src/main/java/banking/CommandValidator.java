package banking;

public class CommandValidator {

	public static final String SAVINGS = "savings";
	public static final String CHECKING = "checking";
	public static final String CD = "cd";
	protected Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String lowerCased = command.toLowerCase();
		String[] parsedString = stringParser(lowerCased);
		if (parsedString.length > 0) {
			return validateDelegator(parsedString);
		}
		return false;
	}

	public String[] stringParser(String command) {
		return command.split(" ");
	}

	public boolean validateDelegator(String[] parsedString) {
		boolean isValidCommand = false;
		switch (parsedString[0]) {
		case ("create"):
			isValidCommand = new CreateValidator(bank).validate(parsedString);
			break;
		case ("deposit"):
			isValidCommand = new DepositValidator(bank).validate(parsedString);
			break;
		case ("withdraw"):
			isValidCommand = new WithdrawValidator(bank).validate(parsedString);
			break;
		case ("transfer"):
			isValidCommand = new TransferValidator(bank).validate(parsedString);
			break;
		case ("pass"):
			isValidCommand = new PassTimeValidator(bank).validate(parsedString);
			break;
		default:
			isValidCommand = false;
		}
		return isValidCommand;
	}

	public boolean checkValidId(String[] string, int index) {
		try {
			int strToInt = Integer.parseInt(string[index]);
			return (string[index].length() == 8 && strToInt >= 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkValidApr(String[] string) {
		try {
			return (Double.parseDouble(string[3]) >= 0 && Double.parseDouble(string[3]) <= 10);
		} catch (Exception exception) {
			return false;
		}
	}

	public String getClass(String[] string) {
		switch (string[1]) {
		case CHECKING:
			return CHECKING;
		case SAVINGS:
			return SAVINGS;
		case CD:
			return CD;
		default:
			return "";

		}
	}

	public boolean checkClass(String[] string) {
		try {
			switch (string[1]) {
			case CHECKING:
			case SAVINGS:
			case CD:
				return true;
			default:
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkExtraParameter(String[] string, int length) {
		return (string.length <= length);
	}

	public boolean checkIdInBank(String[] string) {
		try {
			if (bank.getAccountById(string[2]) == null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean amountChecker(String[] string) {
		try {
			if (Double.parseDouble(string[2]) >= 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
