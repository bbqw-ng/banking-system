import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCDValidatorTest {
	CreateCheckingValidator createCheckingValidator;
	CreateSavingsValidator createSavingsValidator;
	CreateCDValidator createCDValidator;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		createCheckingValidator = new CreateCheckingValidator(bank);
		createSavingsValidator = new CreateSavingsValidator(bank);
		createCDValidator = new CreateCDValidator(bank);
	}

	@Test
	public void create_cd_with_all_parameters_is_valid() {
		boolean actual = createCDValidator.validate("create cd 10002000 7 2000");

		assertTrue(actual);
	}

	@Test
	public void create_two_cd_with_different_ids_is_valid() {
		boolean accountOne = createCDValidator.validate("create cd 10002000 7 5000");
		boolean accountTwo = createCDValidator.validate("create cd 10002001 7 4000");

		assertTrue(accountOne && accountTwo);

	}

	@Test
	public void create_cd_with_decimal_apr_is_valid() {
		boolean actual = createCDValidator.validate("create cd 10002000 0.7 9000");

		assertTrue(actual);
	}

	@Test
	public void create_cd_with_no_apr_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_no_id_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_no_balance_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 9");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_typo_is_invalid() {
		boolean actual = createCDValidator.validate("create ced 10002000 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_two_cd_with_same_id_is_invalid() {
		boolean accountOne = createCDValidator.validate("create cd 10002000 7 9000");
		boolean accountTwo = createCDValidator.validate("create cd 10002000 7 9000");

		assertFalse(accountOne && accountTwo);
	}

	@Test
	public void create_cd_with_too_large_apr_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 999 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_negative_apr_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 -1 9999");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_non_numeric_apr_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 g 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_negative_balance_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 3 -999");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_over_balance_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 7 90000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_non_numeric_balance_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 7 AAAA");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_characters_as_id_is_invalid() {
		boolean actual = createCDValidator.validate("create cd AADASDA 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_characters_and_numbers_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 100AA000 7 9000");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_extra_parameters_is_invalid() {
		boolean actual = createCDValidator.validate("create cd 10002000 7 9000 G");

		assertFalse(actual);
	}

	@Test
	public void create_cd_with_negative_id_is_invalid() {
		boolean actual = createCDValidator.validate("create cd -1002000 7 9000");

		assertFalse(actual);
	}
}
