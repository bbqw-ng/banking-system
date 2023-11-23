import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {
	@Test
	public void checking_account_balance_is_0_when_created() {
		Checking checking = new Checking("10002000", 10);
		double actual = checking.getBalance();

		assertEquals(BankAccount.CHECKING_AND_SAVINGS_DEFAULT_BALANCE, actual);
	}
}
