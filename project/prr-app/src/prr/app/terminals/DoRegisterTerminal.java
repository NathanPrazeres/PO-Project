package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.Cores_DuplicateTerminalKeyException;
import prr.exceptions.Cores_InvalidTerminalKeyException;
import prr.exceptions.Cores_UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("terminalKey", Prompt.terminalKey());
		addOptionField("terminalType", Prompt.terminalType(), "BASIC", "FANCY");
		addStringField("clientKey", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
        try {
			_receiver.registerTerminal(
					optionField("terminalType"),
					stringField("terminalKey"),
					stringField("clientKey"));
		}

		catch (Cores_DuplicateTerminalKeyException e) {
			throw new DuplicateTerminalKeyException(stringField("terminalKey"));
		}
		catch (Cores_InvalidTerminalKeyException e) {
			throw new InvalidTerminalKeyException(stringField("terminalKey"));
		}
		catch (Cores_UnknownClientKeyException e) {
			throw new UnknownClientKeyException(stringField("clientKey"));
		}
	}
}
