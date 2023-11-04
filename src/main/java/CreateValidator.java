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

						float aprConvertToFloat = Float.parseFloat(parsedString[3]);

						try {
							if (!(bank.retrieveAccountById(parsedString[2]) == null)) {
								return false;
							}
						} catch (Exception exception) {
							switch (parsedString[1]) {
							case "savings":
								savings = new Savings(parsedString[2], aprConvertToFloat);
								bank.addAccount(parsedString[2], checking);
							case "checking":
								checking = new Checking(parsedString[2], aprConvertToFloat);
								bank.addAccount(parsedString[2], checking);
							}
							return true;
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
			float strToFloat = Float.parseFloat(string[3]);
			return (strToFloat >= 0 && strToFloat <= 10);
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

}
