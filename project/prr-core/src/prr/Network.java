package prr;

import java.io.Serializable;
import java.io.IOException;

import prr.clients.Client;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.Terminal;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.DuplicateFormatFlagsException;
import java.util.Map;
import java.util.TreeMap;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private int _totalComms = 0;
	private Map<String, Client> _clients = new TreeMap<String, Client>();
	private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>();
        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods


	

	public void registerClient(String id, String name, int nif) throws DuplicateClientKeyException {
		if (_clients.containsKey(id)) {
			throw new DuplicateClientKeyException(id);
		}
		_clients.put(id, new Client(id, name, nif));
	}

	public void registerTerminal(String type, String id, String clientId) throws 
			InvalidTerminalKeyException, DuplicateTerminalKeyException, UnknownClientKeyException {
		if (_terminals.containsKey(id)) {
			throw new DuplicateTerminalKeyException(id);
		}
		if (id.length() != 6 || !id.matches("[^0-9]+")) {
			throw new InvalidTerminalKeyException(id);
		}
		if (!_clients.containsKey(clientId)) {
			throw new UnknownClientKeyException(clientId);
		}
		Terminal terminal;
		if (type.equals("BASIC")) {
			terminal = new Basic(id, clientId);
		}
		else {
			terminal = new Fancy(id, clientId);
		}
		_terminals.put(id, terminal);
		_clients.get(clientId).addTerminal(terminal);
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, ClassNotFoundException /* FIXME maybe other exceptions */  {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				read(line);
			}
		}
		catch (IOException e) {
			throw new IOException(filename);
		}
		catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(filename);
		}
    }

	public void read(String line) throws UnrecognizedEntryException {
		String[] fields = line.split("\\|");
		switch(fields[0]) {
			case "CLIENT" -> readClient(fields);
			case "BASIC", "FANCY" -> readTerminal(fields);
			case "FRIENDS" -> readFriends(fields);
			default -> throw new UnrecognizedEntryException(line); 
		}
	}

	// CLIENT|id|name|nif
	public void readClient(String[] fields) {
		checkFieldsLength(fields, 4);
		String id = fields[1];
		String name = fields[2];
		int nif = Integer.parseInt(fields[3]);
		Client client = new Client(id, name, nif);
		_clients.put(id, client);
	}

	// terminal|id|idClient|state
	public void readTerminal(String[] fields) {
		checkFieldsLength(fields, 4);
		String type = fields[0];
		String id = fields[1];
		String idClient = fields[2];

		registerTerminal(type, id, idClient);
		_terminals.get(id).setState(fields[3]);
	}

	// FRIENDS|id|id1,...,idn
	public void readFriends(String[] fields) {
		checkFieldsLength(fields, 3);
		try {
			String id = fields[1];
			String[] friends = fields[2].split(",");
			for (String friend : friends) {
				_terminals.get(id).addFriend(_terminals.get(friend));
			}
		}
		catch (OtherException e) {
			throw new UnrecognizedEntryException();
		}
	}

	public void checkFieldsLength(String[] fields, int length) throws UnrecognizedEntryException {
		if (fields.length != length) {
			throw new UnrecognizedEntryException();
		}
	}

	//Function to get a string with all the terminals
	public String showAllTerminals() {
		String result = "";
		for (Terminal terminal : _terminals.values()) {
		//order terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friendn
			result += terminal.getType() + "|" + terminal.getId() + "|" + terminal.getClientId() + "|" + terminal.getState() + "|" + terminal.getBalancePaid() + "|" + terminal.getBalanceDebts();
			if (terminal.getFriends().size() > 0) {
				result += "|";
				for (Terminal friend : terminal.getFriends()) {
					result += friend.getId() + ",";
				}
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}
}