package banking;

public class PassTimeValidator extends CommandValidator {

	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		return (super.checkPassTime(parsedString) && checkMonthsExists(parsedString) && checkMonthInt(parsedString)
				&& checkMonthAmount(parsedString));
	}

	public boolean checkMonthsExists(String[] parsedString) {
		try {
			int months = Integer.parseInt(parsedString[1]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkMonthAmount(String[] parsedString) {
		int months = Integer.parseInt(parsedString[1]);
		return (months > 0 && months <= 60);
	}

	public boolean checkMonthInt(String[] parsedString) {
		try {
			int months = Integer.parseInt(parsedString[1]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
