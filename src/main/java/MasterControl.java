import java.util.List;

public class MasterControl {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CreateValidator createValidator, DepositValidator depositValidator,
			CommandProcessor commandProcessor, CommandStorage commandStorage) {

		this.createValidator = createValidator;
		this.depositValidator = depositValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		commandStorage.addInvalidCommand("creat checking 12345678 1.0");
		return commandStorage.getInvalidCommands();
	}
}
