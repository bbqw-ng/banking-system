package banking;

public class CommandValidator {

	public static final int CREATE = 0;
	public static final int ID = 2;
	public static final int APR = 3;
	public static final int CLASS_NAME = 1;
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String lowerCaseCommand = turnLowerCase(command);
		String[] parsedString = stringParser(lowerCaseCommand);

		if (checkCreate(parsedString)) {
			if (checkClass(parsedString)) {
				switch (getClass(parsedString)) {
				case ("checking"):
					CreateCheckingValidator createCheckingValidator = new CreateCheckingValidator(bank);
					return createCheckingValidator.validate(parsedString);
				case ("savings"):
					CreateSavingsValidator createSavingsValidator = new CreateSavingsValidator(bank);
					return createSavingsValidator.validate(parsedString);
				case ("cd"):
					CreateCDValidator createCDValidator = new CreateCDValidator(bank);
					return createCDValidator.validate(parsedString);
				}
			}
		} else if (checkDeposit(parsedString)) {
			DepositValidator depositValidator = new DepositValidator(bank);
			return depositValidator.validate(parsedString);
		} else {
			return false;
		}
		return false;
	}

	public String turnLowerCase(String command) {
		return command.toLowerCase();
	}

	public String[] stringParser(String command) {
		return command.split(" ");
	}

	public boolean checkValidId(String[] string) {
		try {
			int strToInt = Integer.parseInt(string[ID]);
			return (string[ID].length() == 8 && strToInt > 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkValidApr(String[] string) {
		try {
			double strToDouble = Double.parseDouble(string[APR]);
			return (strToDouble >= 0 && strToDouble <= 10);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkCreate(String[] string) {
		return (string[CREATE].equals("create"));
	}

	public boolean checkClass(String[] string) {
		try {
			switch (string[CLASS_NAME]) {
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

	public String getClass(String[] string) {
		switch (string[CLASS_NAME]) {
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
			if (!(bank.getAccountById(string[ID]) == null)) {
				return false;
			}
		} catch (Exception exception) {
			return true;
		}
		return true;
	}

	public boolean checkDeposit(String[] string) {
		return (string[0].equals("deposit"));
	}

	public String checkAccountTypeFromBank(String[] string) {
		switch (bank.getAccountById(string[1]).getAccountType()) {
		case "savings":
			return "savings";
		case "checking":
			return "checking";
		case "cd":
			return "cd";
		}
		return "none";
	}
}
