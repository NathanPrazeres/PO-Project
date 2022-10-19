package prr;

import java.io.Serializable;
import java.io.IOException;

import prr.app.exceptions.UnknownTerminalKeyException;
import prr.clients.Client;
import prr.exceptions.*;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;


// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/* private int _totalComms = 0; */
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
	void importFile(String filename) throws UnrecognizedEntryException, IOException  {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				readLine(line);
			}
		}
		catch (IOException e) {
			throw new IOException(filename);
		}
	}

	public void readLine(String line) throws UnrecognizedEntryException {
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
		try {
			checkFieldsLength(fields, 4);
		}
		catch (UnrecognizedEntryException e) {}

		try {
			registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
		}
		catch (DuplicateClientKeyException e) {}
	}

	// terminal|id|idClient|state
	public void readTerminal(String[] fields) {
		try {
			checkFieldsLength(fields, 4);
		}
		catch (UnrecognizedEntryException e) {}

		try {
			registerTerminal(fields[0], fields[1], fields[2]);
		}

		catch (InvalidTerminalKeyException e1) {}

		catch (DuplicateTerminalKeyException e2) {}

		catch (UnknownClientKeyException e3) {}

		_terminals.get(fields[1]).setState(fields[3]);
	}

	// FRIENDS|id|id1,...,idn
	public void readFriends(String[] fields) throws UnrecognizedEntryException {
		try {
			checkFieldsLength(fields, 3);
		}

		catch (UnrecognizedEntryException e) {}

		try {
			String id = fields[1];
			String[] friends = fields[2].split(",");
			if (!_terminals.containsKey(id)) {
				throw new UnknownTerminalKeyException(id);
			}
			for (String friend : friends) {
				if (!_terminals.containsKey(friend)) {
					throw new UnknownTerminalKeyException(friend);
				}
				else {
					_terminals.get(id).addFriend(_terminals.get(friend));
					_terminals.get(friend).addFriend(_terminals.get(id));
				}
			}
		}
		catch (UnknownTerminalKeyException e) {
			throw new UnrecognizedEntryException(Arrays.toString(fields));
		}
	}

	public void checkFieldsLength(String[] fields, int length) throws UnrecognizedEntryException {
		if (fields.length != length) {
			throw new UnrecognizedEntryException(Arrays.toString(fields));
		}
	}


	public String showClient(Client client) {
		return client.toString()/*  + "\n" + client.readNotifications() */;
	}

	//Function to get a string with all the clients
	public String showAllClients() {
		String result = "";
		for (Client client : _clients.values()) {
			result += client.toString();
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}



	//Function to get a string with all the terminals
	public String showAllTerminals() {
		String result = "";
		for (Terminal terminal : _terminals.values()) {
		//order terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friendn
			result += terminal.toString();
			if (terminal.getFriends().size() > 0) {
				result += "|";
				for (Terminal friend : terminal.getFriends()) {
					result += friend.getId() + ",";
				}
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}