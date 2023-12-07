package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {

	CD cd;

	@BeforeEach
	public void setUp() {
		cd = new CD("10002000", 10, 1000);
	}

	@Test
	public void cd_account_can_be_created_with_specified_balance() {
		double actual = cd.getBalance();

		assertEquals(1000, actual);
	}

	@Test
	public void cd_account_has_cannot_withdraw_when_initialized() {
		boolean actual = cd.getAllowWithdraw();

		assertFalse(actual);
	}

	@Test
	public void valid_withdraw_amount_returns_false_when_given_amount_of_900() {
		boolean actual = cd.validWithdrawAmount(900);
		assertFalse(actual);
	}

}
