package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("terminalId", Prompt.terminalId());
		addOptionalField("terminalType", Prompt.terminalType(), "BASIC", "FANCY");
		addStringField("clientId", Prompt.clientId());
	}

	@Override
	protected final void execute() throws CommandException {
        _receiver.registerTerminal(
			optionalField("terminalType"),
			stringField("terminalId"),
			stringField("clientId"));
	}
}
