public class Checking extends BankAccount {
	// methods have been moved into BankAccount for the
	// purpose of an abstraction
	public Checking(String id, double apr) {
		super(id, apr);
		setAccountType("checking");
	}

}
