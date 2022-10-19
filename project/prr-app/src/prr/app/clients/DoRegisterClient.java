package prr.app.clients;

import prr.Network;
import prr.app.exceptions.DuplicateClientKeyException;
import prr.exceptions.Cores_DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
		addStringField("clientKey", Prompt.key());
		addStringField("clientName", Prompt.name());
		addIntegerField("nif", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.registerClient(
					stringField("clientKey"),
					stringField("clientName"),
					integerField("nif"));
		} catch (Cores_DuplicateClientKeyException e) {
			throw new DuplicateClientKeyException(stringField("clientId"));
		}
	}
}
