public class CreateCDValidator extends CreateValidator {
	private Bank bank;
	private CD cd;

	public CreateCDValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String lowerCaseCommand = turnLowerCase(command);
		String[] parsedString = stringParser(lowerCaseCommand);

		if (checkCreate(parsedString)) {
			if (checkClass(parsedString)) {
				if (checkValidId(parsedString)) {
					if (checkValidApr(parsedString)) {
						if (checkBalance(parsedString)) {

							Float aprConvertToFloat = Float.parseFloat(parsedString[3]);
							Float balanceConvertToFloat = Float.parseFloat(parsedString[4]);

							try {
								if (!(bank.retrieveAccountById(parsedString[2]) == null)) {
									return false;
								}
							} catch (Exception exception) {
								cd = new CD(parsedString[2], aprConvertToFloat, balanceConvertToFloat);
								bank.addAccount(parsedString[2], cd);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkBalance(String[] string) {
		try {
			float convertBalance = Float.parseFloat(string[4]);
			return (convertBalance >= 1000 && convertBalance <= 10000);
		} catch (Exception exception) {
			return false;
		}
	}

}
