import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CDTest {

	@Test
	public void cd_account_can_be_created_with_specified_balance() {
		CD cd = new CD("10002000", 10, 1000);
		double actual = cd.getBalance();

		assertEquals(1000, actual);
	}
}
