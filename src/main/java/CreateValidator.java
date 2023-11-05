public class CreateValidator {
	private Bank bank;
	private Checking checking;
	private Savings savings;

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
								if (addAccountIntoBank(parsedString)) {
									return true;
								}
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
			int strToInt = Integer.parseInt(string[2]);
			return (string[2].length() == 8);
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

	public boolean checkCreate(String[] string) {
		return (string[0].equals("create"));
	}

	public boolean checkClass(String[] string) {
		switch (string[1]) {
		case "checking":
		case "savings":
		case "cd":
			return true;
		default:
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
			if (!(bank.retrieveAccountById(string[2]) == null)) {
				return false;
			}
		} catch (Exception exception) {
			return true;
		}
		return true;
	}

	public boolean addAccountIntoBank(String[] string) {
		Double aprConvertToDouble = Double.parseDouble(string[3]);
		switch (string[1]) {
		case "savings":
			savings = new Savings(string[2], aprConvertToDouble);
			bank.addAccount(string[2], checking);
		case "checking":
			checking = new Checking(string[2], aprConvertToDouble);
			bank.addAccount(string[2], checking);
		}
		return true;
	}

}
