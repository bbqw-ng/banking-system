public class CreateValidator {
	private Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;

	}

	public boolean validate(String command) {
		return true;
		// to test case insensitivity, you wanna create another helper function that
		// turns the string into lower case first. :))
		// so basically to test each category (class, id, apr, balance (for cd only)
		// remember to create breaks and things
		// you want to create helper functions to check the ids and see if it is unique
		// and 8 digit.
		// you want to check if apr between 0 or 10.
		// you want to check the balance for starting between 1000 and 10000.
		// check if class is valid ( a child of BankAccount parent class)
		// make a switch to test if it is either "checking" "savings" or "cd"
		// if class = 999io
		// switch will test if 999io == "checking" , final case = break false.
		// if statement that tests if it is cd, if it is then it goes through the
		// conditional
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
			int strToInt = Integer.parseInt(array[3]);
			return (strToInt >= 0 && strToInt <= 10);
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean checkCreate(String[] array) {
		return (array[0].equals("create"));
	}
}
