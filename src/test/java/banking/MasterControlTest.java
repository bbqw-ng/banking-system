package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List<String> input;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
		input = new ArrayList<>();

	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	public void creating_a_cd_and_withdrawing_from_it_in_twelve_months() {
		input.add("create cd 10001000 1 1000");
		input.add("withdraw 10001000 20");
		input.add("paSs 11");
		input.add("withdraw 10001000 5000");
		input.add("pass 1");
		input.add("withdraw 10001000 10000");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Cd 10001000 0.00 1.00", actual.get(0));
		assertEquals("withdraw 10001000 10000", actual.get(1));
		assertEquals("withdraw 10001000 20", actual.get(2));
		assertEquals("withdraw 10001000 5000", actual.get(3));
	}

	@Test
	public void create_commands_do_not_show_up_in_list() {
		input.add("create cd 10001000 1 1000");
		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Cd 10001000 1000.00 1.00", actual.get(0));

	}

	@Test
	public void pass_time_commands_do_not_show_up_in_list() {
		input.add("pass 1");
		List<String> actual = masterControl.start(input);

		assertEquals(0, actual.size());
	}

	@Test
	public void create_checking_does_not_show_up_on_commands() {
		input.add("create checking 10001000 1");
		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Checking 10001000 0.00 1.00", actual.get(0));
	}

	@Test
	public void depositing_into_checking_shows_up_on_commands() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1000");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Checking 10001000 1000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
	}

	@Test
	public void depositing_into_checking_twice_shows_up_on_commands() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1000");
		input.add("deposit 10001000 1000");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 10001000 2000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("deposit 10001000 1000", actual.get(2));
	}

	@Test
	public void depositing_into_checking_under_limit_shows_up_as_invalid_on_commands() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 -1");
		input.add("deposit 10001000 1000");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 10001000 1000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("deposit 10001000 -1", actual.get(2));
	}

	@Test
	public void depositing_into_checking_over_limit_shows_up_as_invalid_on_commands() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1001");
		input.add("deposit 10001000 1000");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 10001000 1000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("deposit 10001000 1001", actual.get(2));
	}

	@Test
	public void withdrawing_from_checking_shows_up_on_commands() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1000");
		input.add("withdraw 10001000 400");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Checking 10001000 600.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("withdraw 10001000 400", actual.get(2));
	}

	@Test
	public void withdrawing_twice_from_checking_shows_up_on_commands() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1000");
		input.add("withdraw 10001000 400");
		input.add("withdraw 10001000 400");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 10001000 200.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("withdraw 10001000 400", actual.get(2));
		assertEquals("withdraw 10001000 400", actual.get(3));

	}

	@Test
	public void withdrawing_from_checking_under_limit_shows_up_as_invalid() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1000");
		input.add("withdraw 10001000 -1");
		input.add("withdraw 10001000 400");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 10001000 600.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("withdraw 10001000 400", actual.get(2));
		assertEquals("withdraw 10001000 -1", actual.get(3));
	}

	@Test
	public void withdrawing_from_checking_over_limit_shows_up_as_invalid() {
		input.add("create checking 10001000 1");
		input.add("deposit 10001000 1000");
		input.add("withdraw 10001000 2000");
		input.add("withdraw 10001000 400");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 10001000 600.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("withdraw 10001000 400", actual.get(2));
		assertEquals("withdraw 10001000 2000", actual.get(3));
	}

	@Test
	public void transferring_from_checking_to_checking_is_valid() {
		input.add("create checking 10001000 1");
		input.add("create checking 20001000 1");
		input.add("deposit 10001000 1000");
		input.add("withdraw 10001000 AAADASD");
		input.add("trAnSfer 10001000 20001000 200");

		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Checking 10001000 800.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("trAnSfer 10001000 20001000 200", actual.get(2));
		assertEquals("Checking 20001000 200.00 1.00", actual.get(3));
		assertEquals("trAnSfer 10001000 20001000 200", actual.get(4));
		assertEquals("withdraw 10001000 AAADASD", actual.get(5));
	}

	@Test
	public void transferring_from_checking_to_savings_is_valid() {
		input.add("create checking 10001000 1");
		input.add("create savings 20001000 1");
		input.add("deposit 10001000 1000");
		input.add("transfer 10001000 20001000 400");

		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Checking 10001000 600.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("transfer 10001000 20001000 400", actual.get(2));
		assertEquals("Savings 20001000 400.00 1.00", actual.get(3));
		assertEquals("transfer 10001000 20001000 400", actual.get(4));
	}

	@Test
	public void transferring_from_checking_to_cd_is_invalid() {
		input.add("create checking 10001000 1");
		input.add("create cd 20001000 1 1000");
		input.add("deposit 10001000 1000");
		input.add("transfer 10001000 20001000 400");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 10001000 1000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("Cd 20001000 1000.00 1.00", actual.get(2));
		assertEquals("transfer 10001000 20001000 400", actual.get(3));
	}

	@Test
	public void transferring_over_limit_from_checking_is_invalid() {
		input.add("create checking 10001000 1");
		input.add("create savings 20001000 1");
		input.add("deposit 10001000 1000");
		input.add("transfer 10001000 20001000 500");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 10001000 1000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("Savings 20001000 0.00 1.00", actual.get(2));
		assertEquals("transfer 10001000 20001000 500", actual.get(3));
	}

	@Test
	public void transferring_under_limit_from_checking_to_is_invalid() {
		input.add("create checking 10001000 1");
		input.add("create savings 20001000 1");
		input.add("deposit 10001000 1000");
		input.add("transfer 10001000 20001000 -2");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 10001000 1000.00 1.00", actual.get(0));
		assertEquals("deposit 10001000 1000", actual.get(1));
		assertEquals("Savings 20001000 0.00 1.00", actual.get(2));
		assertEquals("transfer 10001000 20001000 -2", actual.get(3));
	}

	@Test
	public void create_savings_does_not_show_up_on_commands() {

	}

	@Test
	public void depositing_into_savings_shows_up_on_commands() {

	}

	@Test
	public void depositing_into_savings_twice_shows_up_on_commands() {

	}

	@Test
	public void depositing_into_savings_under_limit_shows_up_as_invalid_on_commands() {

	}

	@Test
	public void depositing_into_savings_over_limit_shows_up_as_invalid_on_commands() {

	}

	@Test
	public void withdrawing_from_savings_shows_up_on_commands() {

	}

	@Test
	public void withdrawing_twice_from_savings_shows_up_on_commands() {

	}

	@Test
	public void withdrawing_from_savings_under_limit_shows_up_as_invalid() {

	}

	@Test
	public void withdrawing_from_savings_over_limit_shows_up_as_invalid() {

	}

	@Test
	public void transferring_from_savings_to_checking_is_valid() {

	}

	@Test
	public void transferring_from_savings_to_savings_is_valid() {

	}

	@Test
	public void transferring_from_savings_to_cd_is_invalid() {

	}

	@Test
	public void transferring_over_limit_from_savings_is_invalid() {

	}

	@Test
	public void transferring_under_limit_from_savings_to_is_invalid() {

	}

	@Test
	public void create_cd_does_not_show_up_on_commands() {

	}

	@Test
	public void depositing_into_cd_shows_up_on_commands() {

	}

	@Test
	public void depositing_into_cd_twice_shows_up_on_commands() {

	}

	@Test
	public void depositing_into_cd_under_limit_shows_up_as_invalid_on_commands() {

	}

	@Test
	public void depositing_into_cd_over_limit_shows_up_as_invalid_on_commands() {

	}

	@Test
	public void withdrawing_from_cd_shows_up_on_commands() {

	}

	@Test
	public void withdrawing_twice_from_cd_shows_up_on_commands() {

	}

	@Test
	public void withdrawing_from_cd_under_limit_shows_up_as_invalid() {

	}

	@Test
	public void withdrawing_from_cd_over_limit_shows_up_as_invalid() {

	}

	@Test
	public void transferring_from_cd_to_checking_is_invalid() {

	}

	@Test
	public void transferring_from_cd_to_savings_is_invalid() {

	}

	@Test
	public void transferring_from_cd_to_cd_is_invalid() {

	}

	@Test
	public void transferring_over_limit_from_cd_is_invalid() {

	}

	@Test
	public void transferring_under_limit_from_cd_to_is_invalid() {

	}

	@Test
	public void withdrawing_twice_in_a_month_from_savings_is_not_valid() {

	}

	@Test
	public void withdrawing_twice_before_and_after_a_month_from_savings_is_valid() {

	}

	@Test
	public void withdrawing_account_balance_will_delete_account_after_a_month() {

	}

}
