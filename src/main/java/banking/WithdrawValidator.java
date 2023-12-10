package banking;

public class WithdrawValidator extends CommandValidator {

	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] parsedString) {
		return (parsedString[0].equals("withdraw") && super.checkValidId(parsedString, 1)
				&& super.checkExtraParameter(parsedString, 3) && super.amountChecker(parsedString)
				&& checkIdInBank(parsedString) && bank.getAccountById(parsedString[1]).getAllowWithdraw()
				&& validateWithdrawAmount(parsedString));
	}

	public boolean validateWithdrawAmount(String[] string) {
		return (bank.getAccountById(string[1]).validWithdrawAmount(Double.parseDouble(string[2])));
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
