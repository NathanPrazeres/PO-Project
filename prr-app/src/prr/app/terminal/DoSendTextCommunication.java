package prr.app.terminal;

import javax.swing.text.AbstractDocument.Content;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.Cores_UnavailableTerminalException;
import prr.exceptions.Cores_UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("terminalKey", Prompt.terminalKey());
                addStringField("message", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        _receiver.sendTextCommunication(_network, stringField("terminalKey"), stringField("message"));
                } catch (Cores_UnavailableTerminalException e){
                        _display.popup(Message.destinationIsOff(stringField("terminalKey")));
                } catch (Cores_UnknownTerminalKeyException e) {
                        throw new UnknownTerminalKeyException(stringField("terminalKey"));
                }
        }
} 
