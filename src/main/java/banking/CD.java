package banking;

public class CD extends BankAccount {

	public CD(String id, double apr, double balance) {
		super(id, apr, balance);
		setAccountType("cd");
	}

	@Override
	public boolean validDepositAmount(double amnt) {
		return false;
	}

	@Override
	public boolean validWithdrawAmount(double amnt) {
		return (amnt == getBalance());
	}

}
