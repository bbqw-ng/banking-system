public class CreateValidator {
	public static final int CREATE = 0;
	public static final int ID = 2;
	public static final int APR = 3;
	public static final int CLASS_NAME = 1;

	private Bank bank;
	private Checking checking;
	private Savings savings;
	private CD cd;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String lowerCaseCommand = turnLowerCase(command);
		String[] parsedString = stringParser(lowerCaseCommand);

		if (checkCreate(parsedString)) {
			if (checkClass(parsedString)) {
				if (checkValidId(parsedString)) {
					if (checkValidApr(parsedString)) {
						if (checkExtraParameter(parsedString)) {
							if (checkIdInBank(parsedString)) {
								return addAccountIntoBank(parsedString);
							}
						}
					}
				}
			}
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
			if (!(bank.retrieveAccountById(string[ID]).getAccountId() == null)) {
				return false;
			}
		} catch (Exception exception) {
			return true;
		}
		return true;
	}

	public boolean addAccountIntoBank(String[] string) {
		double aprConvertToDouble = Double.parseDouble(string[APR]);
		switch (string[CLASS_NAME]) {
		case "savings":
			savings = new Savings(string[ID], aprConvertToDouble);
			bank.addAccount(string[ID], checking);
			break;
		case "checking":
			checking = new Checking(string[ID], aprConvertToDouble);
			bank.addAccount(string[ID], checking);
			break;
		}
		return true;
	}

	public Bank getBank() {
		return this.bank;
	}

}
