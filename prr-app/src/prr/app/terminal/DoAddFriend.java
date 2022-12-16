package prr.app.terminal;

import prr.Network;
import prr.exceptions.Cores_RepeatedFriendException;
import prr.exceptions.Cores_UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

	DoAddFriend(Network context, Terminal terminal) {
		super(Label.ADD_FRIEND, context, terminal);
		addStringField("terminalKey", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try{
        	_receiver.addFriend(_network, stringField("terminalKey"));
		} catch (Cores_RepeatedFriendException e) {

		} catch (Cores_UnknownTerminalKeyException e){
			throw new UnknownTerminalKeyException(stringField("terminalKey"));
		}
	}
}
