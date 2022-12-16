package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.Cores_AlreadySilenceException;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.getState.turnSilence();
		} catch (Cores_AlreadySilenceException e) {
			_display.popup(Message.alreadySilent());
		}
	}
}
