package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {
	Bank bank;
	PassTimeProcessor passTimeProcessor;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		passTimeProcessor = new PassTimeProcessor(bank);
		checking = new Checking("10001000", 5);
		savings = new Savings("20002000", 5);
		cd = new CD("30003000", 5, 1000);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.addAccount(cd.getAccountId(), cd);
	}

	@Test
	public void 
}
