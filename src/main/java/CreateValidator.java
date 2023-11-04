public class CreateValidator {
	private Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;

	}

	public boolean validate(String command) {
		String lowerCaseCommand = turnLowerCase(command);
		String[] parsedString = stringParser(lowerCaseCommand);
		if (checkCreate(parsedString)) {
			if (checkClass(parsedString)) {
				if (checkValidId(parsedString)) {
					return checkValidApr(parsedString);
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

	public boolean checkValidId(String[] array) {
		try {
			int strToInt = Integer.parseInt(array[2]);
			return (array[2].length() == 8);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkValidApr(String[] array) {
		try {
			float strToFloat = Float.parseFloat(array[3]);
			return (strToFloat >= 0 && strToFloat <= 10);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkCreate(String[] array) {
		return (array[0].equals("create"));
	}

	public boolean checkClass(String[] array) {
		switch (array[1]) {
		case "checking":
		case "savings":
		case "cd":
			return true;
		default:
			return false;
		}
	}
}
