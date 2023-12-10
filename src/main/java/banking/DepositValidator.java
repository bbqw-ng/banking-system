package banking;

public class DepositValidator extends CommandValidator {

	public DepositValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		return (parsedString[0].equals("deposit") && super.checkValidId(parsedString, 1)
				&& super.amountChecker(parsedString) && checkIdInBank(parsedString)
				&& validateDepositAmount(parsedString));
	}

	public boolean validateDepositAmount(String[] string) {
		return (bank.getAccountById(string[1]).validDepositAmount(Double.parseDouble(string[2])));
	}

	@Override
	public boolean checkIdInBank(String[] string) {
		try {
			if (bank.getAccountById(string[1]) != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
