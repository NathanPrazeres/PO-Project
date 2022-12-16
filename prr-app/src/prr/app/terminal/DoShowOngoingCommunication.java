package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.Cores_NoOngoingCommunication;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

	DoShowOngoingCommunication(Network context, Terminal terminal) {
		super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
        try{
			_display.popup(_receiver.showOngoingCommunication());
		} catch (Cores_NoOngoingCommunication e) {
			Message.noOngoingCommunication();
		}
	}
}
