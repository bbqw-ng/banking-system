public abstract class BankAccount {
	public static final int CHECKING_AND_SAVINGS_DEFAULT_BALANCE = 0;
	public static int nextId = 10000000;

	public int id = 10000000;
	private double balance;
	private double apr;

	// this constructor is for the checking and savings
	public BankAccount(double apr) {
		this.apr = apr;
		this.id = nextId;
		nextId++;
	}

	// this constructor is specifically for CD class
	public BankAccount(double balance, double apr) {
		this.balance = balance;
		this.apr = apr;
		this.id = nextId;
		nextId++;
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

	public int getAccountId() {
		return id;
	}
}
