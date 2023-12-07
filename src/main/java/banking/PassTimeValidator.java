package banking;

public class PassTimeValidator extends CommandValidator {

	public static final int MONTH = 1;

	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		return (super.checkPassTime(parsedString) && checkMonthsExists(parsedString) && checkMonthAmount(parsedString));
	}

	public boolean checkMonthsExists(String[] parsedString) {
		try {
			int months = Integer.parseInt(parsedString[MONTH]);
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
