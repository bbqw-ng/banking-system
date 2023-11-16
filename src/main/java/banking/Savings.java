package banking;

public class Savings extends BankAccount {
	// other methods have already been implemented in banking.BankAccount
	public Savings(String id, double apr) {
		super(id, apr);
		setAccountType("savings");
	}

}
