import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCheckingValidatorTest {

	CreateCheckingValidator createCheckingValidator;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		createCheckingValidator = new CreateCheckingValidator(bank);
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
		boolean accountTwo = createCheckingValidator.validate("create checking 10003000 7");

		boolean trueOrFalse = false;
		if (accountOne && accountTwo) {
			trueOrFalse = true;
		}

		assertTrue(trueOrFalse);
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
	public void create_two_checkings_with_same_id_is_invalid() {

	}
}
