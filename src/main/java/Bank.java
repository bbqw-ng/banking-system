import java.util.HashMap;
import java.util.Map;

public class Bank {
	// NOTE: RETHINK THE PARAMS OF THE MAP!
	private Map<Integer, BankAccount> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<Integer, BankAccount> getAccounts() {
		return accounts;
	}

	public void addAccount(int id, BankAccount account) {
		accounts.put(id, account);
	}

	public Object retrieveAccount(int id) {
		return accounts.get(id);
	}

	public void deposit(int id, double value) {
		accounts.get(id).doDeposit(value);
	}

	public void withdraw(int id, double value) {
		accounts.get(id).doWithdraw(value);
	}

	public double checkBalance(int id) {
		return accounts.get(id).getBalance();
	}
}
