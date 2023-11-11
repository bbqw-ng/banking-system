public class CreateSavingsValidator extends CommandValidator {

	public CreateSavingsValidator(Bank bank) {
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
