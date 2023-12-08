package banking;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {

	private Map<String, BankAccount> accounts;

	public Bank() {
		accounts = new LinkedHashMap<>();
	}

	public Map<String, BankAccount> getAccounts() {
		return accounts;
	}

	public void addAccount(String id, BankAccount account) {
		accounts.put(id, account);
	}

	public BankAccount getAccountById(String id) {
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

	public void pass(int months) {
		for (int i = months; i > 0; i--) {
			Iterator<Map.Entry<String, BankAccount>> iterator = accounts.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, BankAccount> entry = iterator.next();
				BankAccount account = entry.getValue();
				if (account.getBalance() == 0) {
					iterator.remove();
				} else {
					if (account.getBalance() < 100) {
						account.doWithdraw(25);
					}
					if ("savings".equals(account.getAccountType())) {
						account.canWithdraw(true);
					} else if ("cd".equals(account.getAccountType())) {
						account.addMonthsPassed(1);
						if (account.getMonthsPassed() >= 12) {
							account.canWithdraw(true);
						}

					}
					account.calculateAPR(account.getBalance(), account.getApr());
				}
			}
		}
	}
}
