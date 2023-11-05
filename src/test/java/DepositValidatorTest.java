import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	Bank bank;
	DepositValidator depositValidator;
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		depositValidator = new DepositValidator(bank);
		checking = new Checking("10001000", 10);
		savings = new Savings("20002000", 10);
		cd = new CD("30003000", 10, 2000);
		bank.addAccount(checking.getAccountId(), checking);
		bank.addAccount(savings.getAccountId(), savings);
		bank.addAccount(cd.getAccountId(), cd);
	}

	@Test
	public void just_deposit_is_invalid() {
		boolean actual = depositValidator.validate("deposit");

		assertFalse(actual);
	}

	@Test
	public void money_can_be_deposited_into_checking_account_is_valid() {
		boolean actual = depositValidator.validate("Deposit 10001000 800");

		assertTrue(actual);
	}

	@Test
	public void money_can_be_deposited_into_savings_account_is_valid() {
		boolean actual = depositValidator.validate("Deposit 20002000 800");

		assertTrue(actual);
	}
}
