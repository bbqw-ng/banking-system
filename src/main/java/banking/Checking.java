package banking;

public class Checking extends BankAccount {

	public Checking(String id, double apr) {
		super(id, apr);
		setAccountType("checking");
		canWithdraw(true);
	}

	@Override
	public boolean validDepositAmount(double amnt) {
		return (amnt >= 0 && amnt <= 1000);
	}

	@Override
	public boolean validWithdrawAmount(double amnt) {
		return (amnt >= 0 && amnt <= 400);
	}

	@Override
	public void canWithdraw(boolean check) {
		allowWithdraw = true;
	}

	@Override
	public void calculateAPR(double balance, double apr) {
		double aprConvertToPercentage = getApr() / 100;
		doDeposit(getBalance() * (aprConvertToPercentage / 12));
	}
}
