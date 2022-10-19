package prr.app.terminals;

import prr.Network;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.Cores_UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("terminalId", Prompt.terminalKey());
		addOptionField("terminalType", Prompt.terminalType(), "BASIC", "FANCY");
		addStringField("clientId", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
        try {
			_receiver.registerTerminal(
					optionField("terminalType"),
					stringField("terminalId"),
					stringField("clientId"));
		}

		catch (DuplicateTerminalKeyException | InvalidTerminalKeyException | Cores_UnknownClientKeyException e) {}


	}
}
