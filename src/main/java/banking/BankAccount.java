package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
	public static final int CHECKING_AND_SAVINGS_DEFAULT_BALANCE = 0;
	protected boolean allowWithdraw;
	private String id;
	private double balance;
	private double apr;
	private String accountType;
	private int monthsPassed;
	private List<String> associatedCommands = new ArrayList<>();

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

	public boolean getAllowWithdraw() {
		return allowWithdraw;
	}

	public int getMonthsPassed() {
		return monthsPassed;
	}

	public void addMonthsPassed(int months) {
		monthsPassed += months;
	}

	public void addAssociatedCommand(String command) {
		associatedCommands.add(command);
	}

	public List<String> getAssociatedCommands() {
		return associatedCommands;
	}

	public String getAccountStatus() {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		String accountType = capitalizeAccountType(getAccountType());
		String id = getAccountId();
		String balance = decimalFormat.format(getBalance());
		String apr = decimalFormat.format(getApr());
		System.out.println(String.join(" ", accountType, id, balance, apr));
		return (String.join(" ", accountType, id, balance, apr));
	}

	public String capitalizeAccountType(String accountType) {
		String[] splitString = accountType.split("");
		splitString[0] = splitString[0].toUpperCase();
		return (String.join("", splitString));
	}

	public abstract boolean validDepositAmount(double amnt);

	public abstract boolean validWithdrawAmount(double amnt);

	public abstract void canWithdraw(boolean check);

	public abstract void calculateAPR(double balance, double apr);
}
