package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.Cores_DestinationIsBusy;
import prr.exceptions.Cores_DestinationIsOff;
import prr.exceptions.Cores_DestinationIsSilent;
import prr.exceptions.Cores_UnknownTerminalKeyException;
import prr.exceptions.Cores_UnsupportedAtDestination;
import prr.exceptions.Cores_UnsupportedAtOrigin;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("terminalKey", Prompt.terminalKey());
		addOptionField("commType", Prompt.commType(), "VIDEO", "VOICE");
	}

	@Override
	protected final void execute() throws CommandException {
		try{
			_receiver.startInteractiveCommunication(_network, stringField("terminalKey"), optionField("commType"));
		} catch (Cores_UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(stringField("terminalKey"));
		} catch (Cores_UnsupportedAtOrigin e){
			Message.unsupportedAtOrigin(_receiver.getId(), optionField("commType"));
		} catch (Cores_UnsupportedAtDestination e){
			Message.unsupportedAtDestination(stringField("terminalKey"), optionField("commType"));
		} catch (Cores_DestinationIsOff e){
			Message.destinationIsOff(stringField("terminalKey"));
		} catch (Cores_DestinationIsBusy e){
			Message.destinationIsBusy(stringField("terminalKey"));
		} catch (Cores_DestinationIsSilent e){
			Message.destinationIsSilent(stringField("terminalKey"));
		}
	}
}
