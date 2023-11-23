public class CD extends BankAccount {

	public CD(String id, double apr, double balance) {
		super(id, apr, balance);
		setAccountType("cd");
	}
}
