package banking;

public class Savings extends BankAccount {
	// other methods have already been implemented in banking.BankAccount
	public Savings(String id, double apr) {
		super(id, apr);
		setAccountType("savings");
		canWithdraw(true);
	}

	@Override
	public boolean validDepositAmount(double amnt) {
		return (amnt >= 0 && amnt <= 2500);
	}

	@Override
	public boolean validWithdrawAmount(double amnt) {
		return (amnt >= 0 && amnt <= 1000);
	}

	@Override
	public void canWithdraw(boolean check) {
		allowWithdraw = check;
	}

	@Override
	public void calculateAPR(double balance, double apr) {
		double aprConvertToPercentage = getApr() / 100;
		doDeposit(getBalance() * (aprConvertToPercentage / 12));
	}
}
