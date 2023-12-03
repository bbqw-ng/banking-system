package banking;

public class Checking extends BankAccount {
	// methods have been moved into banking.BankAccount for the
	// purpose of an abstraction
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
}
