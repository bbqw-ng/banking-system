import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCheckingValidatorTest {

	CreateCheckingValidator createCheckingValidator;
	CreateSavingsValidator createSavingsValidator;
	CreateCDValidator createCDValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createCheckingValidator = new CreateCheckingValidator(bank);
		createSavingsValidator = new CreateSavingsValidator(bank);
		createCDValidator = new CreateCDValidator(bank);
	}

	@Test
	public void create_checking_with_all_parameters_is_valid() {
		boolean actual = createCheckingValidator.validate("create checking 10001111 7");

		assertTrue(actual);
	}

	@Test
	public void create_checking_with_decimal_apr_is_valid() {
		boolean actual = createCheckingValidator.validate("create checking 10002222 .9");

		assertTrue(actual);
	}

	@Test
	public void create_two_checking_with_different_ids_is_valid() {
		boolean accountOne = createCheckingValidator.validate("create checking 10002000 7");
		Checking checking = new Checking("10002000", 7);
		if (accountOne) {
			bank.addAccount(checking.getAccountId(), checking);
		}
		boolean accountTwo = createCheckingValidator.validate("create checking 10003000 7");

		assertTrue(accountOne && accountTwo);
	}

	@Test
	public void create_checking_and_savings_and_cd_is_valid() {
		boolean accountOne = createCheckingValidator.validate("create checking 10002001 7");
		Checking checking = new Checking("10002001", 7);
		if (accountOne) {
			bank.addAccount(checking.getAccountId(), checking);
		}
		boolean accountTwo = createSavingsValidator.validate("create savings 10002002 7");
		Savings savings = new Savings("10002002", 7);
		if (accountTwo) {
			bank.addAccount(savings.getAccountId(), savings);
		}
		boolean accountThree = createCDValidator.validate("create cd 10002003 7 5000");

		assertTrue(accountThree && accountOne && accountTwo);
	}

	@Test
	public void create_checking_with_no_apr_or_id_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_no_id_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_no_apr_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking 10002230");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_typo_is_invalid() {
		boolean actual = createCheckingValidator.validate("create cecking 10008833 1");

		assertFalse(actual);
	}

	@Test
	public void create_two_checking_with_same_id_is_invalid() {
		boolean accountOne = createCheckingValidator.validate("create checking 10002000 7");
		Checking checking = new Checking("10002000", 7);
		if (accountOne) {
			bank.addAccount(checking.getAccountId(), checking);
		}
		boolean accountTwo = createCheckingValidator.validate("create checking 10002000 7");

		assertFalse(accountOne && accountTwo);

	}

	@Test
	public void create_checking_with_too_large_apr_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking 10002000 120");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_negative_apr_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking 10002000 -4");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_non_numeric_apr_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking 10002000 abc");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_character_id_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking AAAABBBB 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_character_and_number_id_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking AAAA1111 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_extra_parameter_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking 10002000 7 ABC");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_negative_id_is_invalid() {
		boolean actual = createCheckingValidator.validate("create checking -1 7");

		assertFalse(actual);
	}

}
