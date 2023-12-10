package banking;

public class CD extends BankAccount {

	public CD(String id, double apr, double balance) {
		super(id, apr, balance);
		setAccountType("cd");
		canWithdraw(false);
	}

	@Override
	public boolean validDepositAmount(double amnt) {
		return false;
	}

	@Override
	public boolean validWithdrawAmount(double amnt) {
		return (amnt >= getBalance());
	}

	@Override
	public void canWithdraw(boolean check) {
		allowWithdraw = check;
	}

	@Override
	public void calculateAPR(double balance, double apr) {
		double aprConvertToPercentage = getApr() / 100;
		for (int reps = 0; reps < 4; reps++) {
			doDeposit(getBalance() * (aprConvertToPercentage / 12));
		}
	}

}
