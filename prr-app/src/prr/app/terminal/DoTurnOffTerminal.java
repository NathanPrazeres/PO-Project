package prr.app.terminal;

import prr.Network;
import prr.exceptions.Cores_AlreadyOffException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

	DoTurnOffTerminal(Network context, Terminal terminal) {
		super(Label.POWER_OFF, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
    	try {
			_receiver.getState.turnOff();
		} catch (Cores_AlreadyOffException e) {
			_display.popup(Message.alreadyOff());
		}
	}
}
