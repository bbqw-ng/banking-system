package banking;

public class CreateCheckingValidator extends CommandValidator {

	public CreateCheckingValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] array) {

		if (super.checkValidId(array)) {
			if (super.checkValidApr(array)) {
				if (super.checkExtraParameter(array)) {
					return (super.checkIdInBank(array));
				}
			}
		}
		return false;
	}
}
