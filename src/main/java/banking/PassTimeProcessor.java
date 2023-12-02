package banking;

public class PassTimeProcessor extends CommandProcessor {

	public PassTimeProcessor(Bank bank) {
		super(bank);
	}

	public void process(String[] parsedString) {
		int months = Integer.parseInt(parsedString[1]);
		bank.pass(months);
	}

}
