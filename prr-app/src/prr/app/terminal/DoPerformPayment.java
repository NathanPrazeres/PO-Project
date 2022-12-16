package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.Cores_InvalidCommunicationException;
import prr.exceptions.Cores_UnknownCommunicationKeyException;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("commKey", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
        try{
			_receiver.performPayment(_network, intergerField("commKey"));
		} catch (Cores_InvalidCommunicationException e) {
			Message.invalidCommunication();
		} catch (Cores_UnknownCommunicationKeyException e) {
			
		}
	}
}
