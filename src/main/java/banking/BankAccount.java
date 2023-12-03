package banking;

public abstract class BankAccount {
	public static final int CHECKING_AND_SAVINGS_DEFAULT_BALANCE = 0;
	protected boolean allowWithdraw;
	private String id;
	private double balance;
	private double apr;
	private String accountType;

	// this constructor is for the checking and savings
	public BankAccount(String id, double apr) {
		this.id = id;
		this.apr = apr;
	}

	// this constructor is specifically for banking.CD class
	public BankAccount(String id, double apr, double balance) {
		this.id = id;
		this.apr = apr;
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public double getApr() {
		return apr;
	}

	public void doDeposit(double amount) {
		balance += amount;
	}

	public void doWithdraw(double amount) {
		if (balance >= amount) {
			balance -= amount;
		} else {
			balance = 0;
		}
	}

	public String getAccountId() {
		return id;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String type) {
		this.accountType = type;

	}

	public abstract boolean validDepositAmount(double amnt);

	public abstract boolean validWithdrawAmount(double amnt);

	// savings: withdraw attribute , add a pass time attribute
	public abstract void canWithdraw(boolean check);

}
