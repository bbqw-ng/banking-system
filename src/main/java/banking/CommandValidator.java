package banking;

public class CommandValidator {

	protected Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String lowerCased = command.toLowerCase();
		String[] parsedString = stringParser(lowerCased);
		switch (parsedString[0]) {
		case ("create"):
			CreateValidator createValidator = new CreateValidator(bank);
			return createValidator.validate(parsedString);
		case ("deposit"):
			DepositValidator depositValidator = new DepositValidator(bank);
			return depositValidator.validate(parsedString);
		case ("withdraw"):
			WithdrawValidator withdrawValidator = new WithdrawValidator(bank);
			return withdrawValidator.validate(parsedString);
		case ("transfer"):
			TransferValidator transferValidator = new TransferValidator(bank);
			return transferValidator.validate(parsedString);
		case ("pass"):
			PassTimeValidator passTimeValidator = new PassTimeValidator(bank);
			return passTimeValidator.validate(parsedString);
		default:
			return false;
		}
	}

	public String[] stringParser(String command) {
		return command.split(" ");
	}

	public boolean checkValidId(String[] string) {
		try {
			int strToInt = Integer.parseInt(string[2]);
			return (string[2].length() == 8 && strToInt >= 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkValidApr(String[] string) {
		try {
			double strToDouble = Double.parseDouble(string[3]);
			return (strToDouble >= 0 && strToDouble <= 10);
		} catch (Exception exception) {
			return false;
		}
	}

	public String getClass(String[] string) {
		switch (string[1]) {
		case "checking":
			return "checking";
		case "savings":
			return "savings";
		case "cd":
			return "cd";
		default:
			return "";

		}
	}

	public boolean checkClass(String[] string) {
		try {
			switch (string[1]) {
			case "checking":
			case "savings":
			case "cd":
				return true;
			default:
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkExtraParameter(String[] string) {
		try {
			String test = string[4];
			return false;
		} catch (Exception exceptionOne) {
			return true;
		}
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

}
