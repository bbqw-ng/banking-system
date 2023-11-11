public class DepositValidator {
	private Bank bank;

	public DepositValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String lowerCaseCommand = turnLowerCase(command);
		String[] parsedString = stringParser(lowerCaseCommand);

		if (checkDeposit(parsedString)) {
			if (checkValidId(parsedString)) {
				if (amountChecker(parsedString)) {
					if (checkIdInBank(parsedString)) {
						return (balanceAndAccountChecker(checkAccountTypeFromBank(parsedString), parsedString));
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

	public boolean checkDeposit(String[] string) {
		return (string[0].equals("deposit"));
	}

	public boolean checkValidId(String[] string) {
		try {
			int strToInt = Integer.parseInt(string[1]);
			return (string[1].length() == 8 && strToInt > 0);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkIdInBank(String[] string) {
		try {
			if (bank.getAccountById(string[1]).getAccountId() == null) {
				return false;
			}
			return true;
		} catch (Exception exception) {
			return true;
		}
	}

	public boolean amountChecker(String[] string) {
		try {
			double convertAmount = Double.parseDouble(string[2]);
			if (convertAmount >= 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
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

	public boolean depositLimitChecker(String string, String[] array) {
		Double convertAmount = Double.parseDouble(array[2]);
		if (string.equals("savings")) {
			return (convertAmount >= 0 && convertAmount <= 2500);
		}
		if (string.equals("checking")) {
			return (convertAmount >= 0 && convertAmount <= 1000);
		}
		if (string.equals("cd")) {
			return false;
		}
		return false;
	}

}
