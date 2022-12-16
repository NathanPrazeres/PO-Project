package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.LinkedList;

import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.Cores_DuplicateClientKeyException;
import prr.exceptions.Cores_DuplicateTerminalKeyException;
import prr.exceptions.Cores_InvalidTerminalKeyException;
import prr.exceptions.Cores_UnknownClientKeyException;
import prr.exceptions.Cores_UnknownTerminalKeyException;
import prr.exceptions.Cores_UnknownCommunicationKeyException;
import prr.exceptions.Cores_UnknownTerminalStateException;
import prr.exceptions.Cores_RepeatedFriendException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;


/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	private Map<String, Terminal> _terminals = new TreeMap<>();
	private Map<Integer, Communication> _communications = new TreeMap<>();

	public Client getClient(String id) throws Cores_UnknownClientKeyException{
		Client client = _clients.get(id);
		if (client == null) {
			throw new Cores_UnknownClientKeyException(id);
		}
		return client;
	}

	/**
	 * Finds a terminal in the network
	 * 
	 * @param id									the id of the terminal
	 * @return										the terminal with the given id
	 * @throws Cores_UnknownTerminalKeyException	if the given id doesn't match any terminals in the network
	 */
	public Terminal getTerminal(String id) throws Cores_UnknownTerminalKeyException{
		Terminal terminal = _terminals.get(id);
		if (terminal == null) {
			throw new Cores_UnknownTerminalKeyException(id);
		}
		return terminal;
	}

	public Map<String, Client> getClientsWithoutDebts() {
		Map<String, Client> clientsWithoutDebts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for (Client client : _clients.values()) {
			if (!client.hasDebt()) {
				clientsWithoutDebts.put(client.getId(), client);
			}
		}
		return clientsWithoutDebts;

	}

	public Collection<Client> getClientsWithDebts() {
		Collection<Client> clientsWithDebts = new LinkedList<>();
		for (Client client : _clients.values()) {
			if (client.hasDebt()) {
				clientsWithDebts.add(client);
			}
		}
		return clientsWithDebts.stream().sorted(Comparator.comparing(Client::totalOwed).reversed()).collect(Collectors.toList());
												//c -> c.totalOwed()
	}

	public Collection<Terminal> getTerminalsWithPositiveBalance() {
		Collection<Terminal> terminalsWithPositiveBalance = new LinkedList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.getBalance() > 0) {
				terminalsWithPositiveBalance.add(terminal);
			}
		}
		return terminalsWithPositiveBalance;
	}

	/**
	 * Registers a client to the network
	 * 
	 * @param id									the id of the client
	 * @param name									the name of the client
	 * @param nif									the nif of the client
	 * @throws Cores_DuplicateClientKeyException	if the client id already exists
	 */
	public void registerClient(String id, String name, int nif) throws Cores_DuplicateClientKeyException {
		if (_clients.containsKey(id)) {
			throw new Cores_DuplicateClientKeyException(id);
		}
		_clients.put(id, new Client(id, name, nif));
	}

	/**
	 * Registers a terminal to a client in the network
	 * 
	 * @param type
	 * @param id
	 * @param clientId
	 * @param state
	 * @throws Cores_InvalidTerminalKeyException	when the terminal id is invalid
	 * @throws Cores_DuplicateTerminalKeyException	when a terminal with the same id already exists
	 * @throws Cores_UnknownClientKeyException		when the client id doesn't correspond to a registered client
	 * @throws Cores_UnknownTerminalStateException	when the terminal state is invalid
	 */
	public void registerTerminal(String type, String id, String clientId, String state) throws
			Cores_InvalidTerminalKeyException, Cores_DuplicateTerminalKeyException, Cores_UnknownClientKeyException,
			Cores_UnknownTerminalStateException {

		if (_terminals.containsKey(id)) {
			throw new Cores_DuplicateTerminalKeyException(id);
		}

		if (id.length() != 6 || !id.matches("[0-9]+")) {
			throw new Cores_InvalidTerminalKeyException(id);
		}

		if (!_clients.containsKey(clientId)) {
			throw new Cores_UnknownClientKeyException(clientId);
		}

		Terminal terminal;
		if (type.equals("BASIC")) {
			terminal = new Basic(id, _clients.get(clientId));
		}
		else {
			terminal = new Fancy(id, _clients.get(clientId));
		}

		switch (state){
			case "IDLE", "ON", "OFF", "SILENCE", "BUSY" -> terminal.createState(state);
			default -> throw new Cores_UnknownTerminalStateException(state);
		}

		_terminals.put(id, terminal);
		_clients.get(clientId).addTerminal(terminal);
	}

	public void addCommunication(Communication c) {
		_communications.put(c.getId(), c);
	}

	/**
	 * Establishes a friendship relationship between two terminals
	 *  
	 * @param id									id of the original terminal
	 * @param friends								the list of new friend's ids
	 * @throws Cores_UnknownTerminalKeyException	when the terminal id doesn't correspond to a registered terminal
	 */
	public void registerFriends(String id, String friends) throws Cores_UnknownTerminalKeyException, Cores_RepeatedFriendException {
		if (!_terminals.containsKey(id)) {
			throw new Cores_UnknownTerminalKeyException(id);
		}
		String[] friendsList = friends.split(",");
		for (String friend : friendsList) {
			if (!_terminals.containsKey(friend)) {
				throw new Cores_UnknownTerminalKeyException(friend);
			}
			_terminals.get(id).addFriend(this, friend);
		}
	}

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename						name of the text input file
     * @throws UnrecognizedEntryException	if some entry is not correct
	 * @throws IOException					if there is an IO error while processing the text file
	 * @throws Cores_RepeatedFriendException
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, Cores_RepeatedFriendException  {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				readLine(line);
			}
		}
	}

	/**
	 * Read a line from the text input file and create corresponding domain entities.
	 * 
	 * @param line								line from the text input file
	 * @throws UnrecognizedEntryException		if some entry is not correct
	 * @throws Cores_RepeatedFriendException	if the same friend is added twice
	*/
	public void readLine(String line) throws UnrecognizedEntryException, Cores_RepeatedFriendException {
		String[] fields = line.split("\\|");
		switch(fields[0]) {
			case "CLIENT" -> readClient(fields);
			case "BASIC", "FANCY" -> readTerminal(fields);
			case "FRIENDS" -> readFriends(fields);
			default -> throw new UnrecognizedEntryException(line); 
		}
	}

	/**
	 * Read a client entry from the text input file in the 
	 * "CLIENT|id|name|nif" format and create corresponding domain entities.
	 * 
	 * @param fields						fields from the text input file
	 * @throws UnrecognizedEntryException	if some entry is not correct
	 */
	public void readClient(String[] fields) throws UnrecognizedEntryException {
		try {
			registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
		} catch (Cores_DuplicateClientKeyException e) {
			throw new UnrecognizedEntryException(String.join("|", fields));
		}
	}

	/**
	 * Read a terminal entry from the text input file in the 
	 * "terminal|id|idClient|state" format and create corresponding domain entities.
	 * 
	 * @param fields						fields from the text input file
	 * @throws UnrecognizedEntryException	if some entry is not correct
	 */
	public void readTerminal(String[] fields) throws UnrecognizedEntryException {
		try {
			registerTerminal(fields[0], fields[1], fields[2], fields[3]);
		} catch (Cores_InvalidTerminalKeyException e) {
			throw new UnrecognizedEntryException(String.join("|", fields));
		} catch (Cores_DuplicateTerminalKeyException e) {
			throw new UnrecognizedEntryException(String.join("|", fields));
		} catch (Cores_UnknownClientKeyException e) {
			throw new UnrecognizedEntryException(String.join("|", fields));
		} catch (Cores_UnknownTerminalStateException e) {
			throw new UnrecognizedEntryException(String.join("|", fields));
		}
	}

	/**
	 * Read a friends entry from the text input file in the
	 * "FRIENDS|id|id1,...,idn" format and create corresponding domain entities.
	 * 
	 * @param fields						fields from the text input file
	 * @throws UnrecognizedEntryException	if some entry is not correct
	 * @throws Cores_RepeatedFriendException
	 */
	public void readFriends(String[] fields) throws UnrecognizedEntryException, Cores_RepeatedFriendException {
		try {
			registerFriends(fields[1], fields[2]);
		} catch (Cores_UnknownTerminalKeyException e) {
			throw new UnrecognizedEntryException(String.join("|", fields));
		}
	}

	/*
	type|idCommunication|idSender|idReceiver|units|price|status
 	*/
	public String showAllCommunications() {
		StringBuilder result = new StringBuilder();
		for (Communication communication : _communications.values()) {
			result.append(communication.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	public String showCommunicationsFromClient(String id) throws Cores_UnknownClientKeyException {
		Client client = _clients.get(id);
		StringBuilder result = new StringBuilder();
		for (Communication communication : client.getSentCommunications().values()) {
			result.append(communication.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	public String showCommunicationstoClient(String id) throws Cores_UnknownClientKeyException {
		Client client = _clients.get(id);
		StringBuilder result = new StringBuilder();
		for (Communication communication : client.getReceivedCommunications().values()) {
			result.append(communication.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	public String showClientsWithoutDebts(){
		StringBuilder result = new StringBuilder();
		Map<String, Client> clientsWithoutDebts = getClientsWithoutDebts();
		for (Client client : clientsWithoutDebts.values()) {
			result.append(client.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	public String showClientsWithDebts(){
		StringBuilder result = new StringBuilder();
		Collection<Client> clientsWithDebts = getClientsWithDebts();
		for (Client client : clientsWithDebts) {
			result.append(client.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}	

	public float getClientPayments(String id) throws Cores_UnknownClientKeyException {
		return getClient(id).totalPaid();
	}

	public float getClientDebts(String id) throws Cores_UnknownClientKeyException {
        return getClient(id).totalOwed();
	}

	public long getGlobalPayments() {
		long globalPayments = 0;
		for (Client client : _clients.values()) {
			globalPayments += client.totalPaid();
		}
		return globalPayments;
	}

	public long getGlobalDebts() {
		long globalDebts = 0;
		for (Client client : _clients.values()) {
			globalDebts += client.totalOwed();
		}
		return globalDebts;
	}

	public String showTerminalsWithPositiveBalance(){
		StringBuilder result = new StringBuilder();
		Collection<Terminal> terminalsWithPositiveBalance = getTerminalsWithPositiveBalance();
		for (Terminal terminal : terminalsWithPositiveBalance) {
			result.append(terminal.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}	

	public Communication getCommunication(int id) throws Cores_UnknownCommunicationKeyException{
		Communication communication = _communications.get(id);
		if (communication == null) {
			throw new Cores_UnknownCommunicationKeyException(id);
		}
		return communication;
	}

	/**
	 * Gets the attributes of a client with the given id in the
	 * "CLIENT|id|name|nif|type|notifications|n_terminals|paid|owed"
	 * format
	 * 
	 * @param id								id of the client
	 * @return									string with the information of the client
	 * @throws Cores_UnknownClientKeyException	when the client id doesn't correspond to a registered client
	 */
	public String showClient(String id) throws Cores_UnknownClientKeyException {
		Client client = getClient(id);
		return client.toString() + client.readNotifications();
	}

	/**
	 * Gets the attributes for all clients in the network
	 * 
	 * @return	CLIENT|id|name|nif|type|notifications|n_terminals|paid|owed
	 */
	public String showAllClients() {
		StringBuilder result = new StringBuilder();
		for (Client client : _clients.values()) {
			result.append(client.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	/**
	 * Gets the attributes of all the terminals in the network
	 * 
	 * @return	terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friendn
	 */
	public String showAllTerminals() {
		StringBuilder result = new StringBuilder();
		for (Terminal terminal : _terminals.values()) {
			result.append(terminal.toString());
			result.append("\n");
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}

	/**
	 * Returns a string with all the terminals that don't have any communications
	 * 
	 * @return	string with the information of all the terminals that are unused
	 */
	public String showUnusedTerminals() {
		StringBuilder result = new StringBuilder();
		for (Terminal terminal : _terminals.values()) {
			if (!terminal.getUsed()) {
				result.append(terminal.toString());
				result.append("\n");
			}
		}
		if (!result.isEmpty()) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}
}