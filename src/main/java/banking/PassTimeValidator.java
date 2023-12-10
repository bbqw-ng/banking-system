package banking;

public class PassTimeValidator extends CommandValidator {

	public static final int MONTH = 1;

	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		return (parsedString[0].equals("pass") && super.checkExtraParameter(parsedString, 2)
				&& checkMonthsExists(parsedString) && checkMonthAmount(parsedString));
	}

	public boolean checkMonthsExists(String[] parsedString) {
		try {
			Integer.parseInt(parsedString[MONTH]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkMonthAmount(String[] parsedString) {
		int months = Integer.parseInt(parsedString[MONTH]);
		return (months > 0 && months <= 60);
	}

}
