package prr.app.terminal;

import prr.Network;
import prr.exceptions.Cores_UnknownFriendException;
import prr.exceptions.Cores_UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
		addStringField("terminalKey", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
        try{
        	_receiver.removeFriend(_network, stringField("terminalKey"));
		} catch (Cores_UnknownFriendException e) {

		} catch (Cores_UnknownTerminalKeyException e){
			throw new UnknownTerminalKeyException(stringField("terminalKey"));
		}
	}
}
