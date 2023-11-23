package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

	CreateValidator createValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);
	}

	@Test
	public void create_checking_with_all_parameters_is_valid() {
		boolean actual = createValidator.validate("create checking 10001111 7");

		assertTrue(actual);
	}

	@Test
	public void create_checking_with_decimal_apr_is_valid() {
		boolean actual = createValidator.validate("create checking 10002222 .9");

		assertTrue(actual);
	}

	@Test
	public void create_two_checking_with_different_ids_is_valid() {
		boolean accountOne = createValidator.validate("create checking 10002000 7");
		Checking checking = new Checking("10002000", 7);
		if (accountOne) {
			bank.addAccount(checking.getAccountId(), checking);
		}
		boolean accountTwo = createValidator.validate("create checking 10003000 7");

		assertTrue(accountOne && accountTwo);
	}

	@Test
	public void create_checking_and_savings_and_cd_is_valid() {
		boolean accountOne = createValidator.validate("create checking 10002001 7");
		Checking checking = new Checking("10002001", 7);

		if (accountOne) {
			bank.addAccount(checking.getAccountId(), checking);
		}

		boolean accountTwo = createValidator.validate("create savings 10002002 7");
		Savings savings = new Savings("10002002", 7);

		if (accountTwo) {
			bank.addAccount(savings.getAccountId(), savings);
		}

		boolean accountThree = createValidator.validate("create cd 10002003 7 5000");

		assertTrue(accountThree && accountOne && accountTwo);
	}

	@Test
	public void create_checking_with_no_apr_or_id_is_invalid() {
		boolean actual = createValidator.validate("create checking");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_no_id_is_invalid() {
		boolean actual = createValidator.validate("create checking 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_no_apr_is_invalid() {
		boolean actual = createValidator.validate("create checking 10002230");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_typo_is_invalid() {
		boolean actual = createValidator.validate("create cecking 10008833 1");

		assertFalse(actual);
	}

	@Test
	public void create_two_checking_with_same_id_is_invalid() {
		boolean accountOne = createValidator.validate("create checking 10002000 7");
		Checking checking = new Checking("10002000", 7);
		if (accountOne) {
			bank.addAccount(checking.getAccountId(), checking);
		}
		boolean accountTwo = createValidator.validate("create checking 10002000 7");

		assertFalse(accountOne && accountTwo);

	}

	@Test
	public void create_checking_with_too_large_apr_is_invalid() {
		boolean actual = createValidator.validate("create checking 10002000 120");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_negative_apr_is_invalid() {
		boolean actual = createValidator.validate("create checking 10002000 -4");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_non_numeric_apr_is_invalid() {
		boolean actual = createValidator.validate("create checking 10002000 abc");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_character_id_is_invalid() {
		boolean actual = createValidator.validate("create checking AAAABBBB 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_character_and_number_id_is_invalid() {
		boolean actual = createValidator.validate("create checking AAAA1111 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_extra_parameter_is_invalid() {
		boolean actual = createValidator.validate("create checking 10002000 7 ABC");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_negative_id_is_invalid() {
		boolean actual = createValidator.validate("create checking -1 7");

		assertFalse(actual);
	}

	@Test
	public void create_checking_with_apr_of_ten_is_valid() {
		boolean actual = createValidator.validate("create checking 10002000 10");

		assertTrue(actual);
	}

	@Test
	public void create_checking_with_apr_of_zero_is_valid() {
		boolean actual = createValidator.validate("create checking 10002000 0");

		assertTrue(actual);
	}

	@Test
	public void create_cd_with_all_parameters_is_valid() {
		boolean actual = createValidator.validate("create cd 10002000 7 2000");

		assertTrue(actual);
	}

	@Test
	public void create_two_cd_with_different_ids_is_valid() {
		boolean accountOne = createValidator.validate("create cd 10002000 7 5000");
		boolean accountTwo = createValidator.validate("create cd 10002001 7 4000");

		assertTrue(accountOne && accountTwo);

	}

	@Test
	public void create_cd_with_decimal_apr_is_valid() {
		boolean actual = createValidator.validate("create cd 10002000 0.7 9000");

		assertTrue(actual);
	}

	@Test
	public void create_cd_with_no_apr_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_no_id_is_invalid() {
		boolean actual = createValidator.validate("create cd 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_no_balance_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 9");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_typo_is_invalid() {
		boolean actual = createValidator.validate("create ced 10002000 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_two_cd_with_same_id_is_invalid() {
		boolean accountOne = createValidator.validate("create cd 10002000 7 9000");
		CD cd = new CD("10002000", 7, 9000);
		if (accountOne) {
			bank.addAccount("10002000", cd);
		}
		boolean accountTwo = createValidator.validate("create cd 10002000 7 9000");

		assertFalse(accountOne && accountTwo);
	}

	@Test
	public void create_cd_with_too_large_apr_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 999 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_negative_apr_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 -1 9999");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_non_numeric_apr_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 g 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_negative_balance_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 3 -999");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_over_balance_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 7 90000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_non_numeric_balance_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 7 AAAA");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_characters_as_id_is_invalid() {
		boolean actual = createValidator.validate("create cd AADASDA 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_characters_and_numbers_is_invalid() {
		boolean actual = createValidator.validate("create cd 100AA000 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_extra_parameters_is_invalid() {
		boolean actual = createValidator.validate("create cd 10002000 7 9000 G");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_negative_id_is_invalid() {
		boolean actual = createValidator.validate("create cd -1002000 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_apr_of_ten_is_valid() {
		boolean actual = createValidator.validate("create checking 10002000 10");

		assertTrue(actual);
	}

	@Test
	public void create_cd_with_apr_of_zero_is_valid() {
		boolean actual = createValidator.validate("create checking 10002000 0");

		assertTrue(actual);
	}

}
