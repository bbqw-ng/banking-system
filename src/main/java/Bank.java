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

	public void addAccount(Integer id, BankAccount account) {
		accounts.put(id, account);
	}

	public Object retrieveAccount(int accountId) {
		return accounts.remove(accountId);
	}
}
