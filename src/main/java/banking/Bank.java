package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {

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

	public void closeAccount(String id) {
		accounts.remove(id);
	}

	public void doNormalAprCalc(BankAccount account) {
		double aprConvertToPercentage = account.getApr() / 100;
		double monthlyAprConvert = aprConvertToPercentage / 12;
		double monthlyBalanceGain = account.getBalance() * monthlyAprConvert;
		account.doDeposit(monthlyBalanceGain);
	}

	public void doCdAprCalc(BankAccount account) {
		for (int reps = 0; reps < 4; reps++) {
			double aprConvertToPercentage = account.getApr() / 100;
			double monthlyAprConvert = aprConvertToPercentage / 12;
			double monthlyBalanceGain = account.getBalance() * monthlyAprConvert;
			account.doDeposit(monthlyBalanceGain);
		}
	}

	public void pass(int months) {
		for (int i = months; i > 0; i--) {
			for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
				BankAccount account = entry.getValue();
				if (account.getBalance() == 0) {
					accounts.remove(entry.getKey());
				} else {
					if (account.getBalance() < 100) {
						account.doWithdraw(25);
					}
					if (account.getAccountType().equals("savings") || account.getAccountType().equals("checking")) {
						doNormalAprCalc(account);
					} else if (account.getAccountType().equals("cd")) {
						doCdAprCalc(account);
					}
				}
			}
		}
	}
}
