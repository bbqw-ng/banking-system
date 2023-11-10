import java.util.HashMap;
import java.util.Map;

public class Bank {
	// NOTE: RETHINK THE PARAMS OF THE MAP!
	private Map<String, BankAccount> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, BankAccount> getAccounts() {
		return accounts;
	}

	public void addAccount(String id, BankAccount account) {
		accounts.put(id, account);
	}

	public BankAccount retrieveAccountById(String id) {
		return accounts.get(id);
	}

	public void deposit(String id, double value) {
		accounts.get(id).doDeposit(value);
	}

	public void withdraw(String id, double value) {
		accounts.get(id).doWithdraw(value);
	}

	public double checkBalance(String id) {
		return accounts.get(id).getBalance();
	}

	public BankAccount removeAccount(String id) {
		return accounts.remove(id);
	}

}
