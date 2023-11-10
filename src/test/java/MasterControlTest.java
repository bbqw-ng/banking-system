import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MasterControlTest {

	@Test
	public void typo_in_create_command_is_invalid() {
		List<String> input = new ArrayList<>();
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
	}
}
