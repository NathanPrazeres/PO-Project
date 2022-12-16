package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.Network;
import prr.clients.Client;
import prr.clients.ClientType;
import prr.communications.Communication;
import prr.communications.Text;
import prr.communications.Video;
import prr.communications.Voice;
import prr.exceptions.Cores_DestinationIsBusy;
import prr.exceptions.Cores_DestinationIsOff;
import prr.exceptions.Cores_DestinationIsSilent;
import prr.exceptions.Cores_NoOngoingCommunication;
import prr.exceptions.Cores_RepeatedFriendException;
import prr.exceptions.Cores_UnavailableTerminalException;
import prr.exceptions.Cores_UnknownCommunicationKeyException;
import prr.exceptions.Cores_InvalidCommunicationException;
import prr.exceptions.Cores_UnknownFriendException;
import prr.exceptions.Cores_UnknownTerminalKeyException;
import prr.exceptions.Cores_UnsupportedAtDestination;
import prr.exceptions.Cores_UnsupportedAtOrigin;
import prr.notifications.Notification;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private String _id;
    private Client _client;
    private TerminalState _state = new IdleState(this);
    private Map<String, Terminal> _friends = new TreeMap<>();
    private boolean _used = false;
    private Map<Integer, Communication> _sentCommunications = new TreeMap<>();
    private Map<Integer, Communication> _receivedCommunications = new TreeMap<>();
    private List<Client> _clientsToNotify = new ArrayList<>();
    private Communication _ongoingCommunication = null;
    

    public Terminal(String id, Client client) {
        _id = id;
        _client = client;
    }
    
    public abstract String typeToString();

    public boolean isBasic() { return false; }
    public boolean isFancy() { return false; }

    @Override
    public String toString() {
        StringBuilder friends = new StringBuilder();

        if (getFriends().size() > 0) {
			friends.append("|");
			for (Terminal friend : getFriends()) {
				friends.append(friend.getId());
				friends.append(",");
			}
			friends.deleteCharAt(friends.length() - 1);
		}
        
        return typeToString() + "|" + 
                getId() + "|" + 
                getClient().getId() + "|" + 
                getState() + "|" + 
                Math.round(getValuePaid()) + "|" + 
                Math.round(getValueOwed()) + "|" +
                friends.toString();
    }

    public String getId() {
        return _id;
    }

    public String getStateName() {
        return _state.getStateName();
    }

    public TerminalState getState() {
        return _state;
    }

    public Client getClient() {
        return _client;
    }

    public Collection<Terminal> getFriends() {
        return _friends.values();
    }

    public boolean isFriend(String friendId) {
        return _friends.containsKey(friendId);
    }

    public boolean getUsed() {
        return _used;
    }

    public void setUsed() {
        if (!getUsed()) {
            _used = true;
        }
    }

    public String showTerminalBalance(){
        return String.valueOf(getBalance());
    }

    public void createState(String stateName) {
        switch(stateName) {
            case "IDLE", "ON" -> setState(new IdleState(this));
            case "BUSY" -> setState(new BusyState(this));
            case "SILENCE" -> setState(new SilenceState(this));
            case "OFF" -> setState(new OffState(this));
        }
    }

    public void setOngoingCommunication(Communication c) {
        _ongoingCommunication = c;
    }

    public Communication getOngoingCommunication() {
        return _ongoingCommunication;
    }

    public String showOngoingCommunication() throws Cores_NoOngoingCommunication {
        if (getOngoingCommunication() == null)
            throw new Cores_NoOngoingCommunication();
        return getOngoingCommunication().toString();
    }
    public void setState(TerminalState state) {
        _state = state;
    }
    
    public void addSentCommunication(Communication c) {
        _sentCommunications.put(c.getId(), c);
    }

    public void addReceivedCommunication(Communication c) {
        _receivedCommunications.put(c.getId(), c);
    }

    public ClientType getClientType() {
        return getClient().getType();
    }

    public Map<Integer, Communication> getSentCommunications() {
        return _sentCommunications;
    }

    public Map<Integer, Communication> getReceivedCommunications() {
        return _receivedCommunications;
    }

    public void addClientToNotify(Client client) {
        _clientsToNotify.add(client);
    }

    public List<Client> getClientsToNotify() {
        return _clientsToNotify;
    }

    public void sendNotifications(Notification n) {
        for (Client client : getClientsToNotify()) {
            if (client.getNotifActive()) {
                client.getDeliveryMethod().deliverNotification(client, n);
            }
        }
        getClientsToNotify().clear();
    }
    public void handleStartInteractiveCommunicationExceptions(Terminal receiver, String type)throws Cores_UnsupportedAtOrigin,
    Cores_UnsupportedAtDestination, Cores_DestinationIsOff, Cores_DestinationIsBusy, Cores_DestinationIsSilent{
        if (isBasic() && type.equals("VIDEO"))
            throw new Cores_UnsupportedAtOrigin(_id);
        if (receiver.isBasic() && type.equals("VIDEO"))
            throw new Cores_UnsupportedAtDestination(receiver.getId());
        if (receiver.getState().isOff())
            throw new Cores_DestinationIsOff(receiver.getId());
        if (receiver.getState().isBusy())
            throw new Cores_DestinationIsBusy(receiver.getId());
        if (receiver.getState().isSilence())
            throw new Cores_DestinationIsSilent(receiver.getId());
    }

    public void startInteractiveCommunication(Network n, String receiverId, String type) throws Cores_UnknownTerminalKeyException,
    Cores_UnsupportedAtOrigin, Cores_UnsupportedAtDestination, Cores_DestinationIsOff, Cores_DestinationIsBusy, Cores_DestinationIsSilent{
        Terminal receiver = n.getTerminal(receiverId);
        handleStartInteractiveCommunicationExceptions(receiver, type);
        Communication c;
        switch (type) {
            case "VIDEO": 
                c = new Video(this, receiver);
                setOngoingCommunication(c);
                receiver.setOngoingCommunication(c);
                break;
            case "VOICE":
                c = new Voice(this, receiver);
                setOngoingCommunication(c);
                receiver.setOngoingCommunication(c);
        }
        addSentCommunication(getOngoingCommunication());
        receiver.addReceivedCommunication(getOngoingCommunication());
        setUsed();
        receiver.setUsed();
        getState().turnBusy();
        receiver.getState().turnBusy();
    }

    public void endInteractiveCommunication(Network n, int duration) {
        Terminal receiver = getOngoingCommunication().getReceiver();
        getOngoingCommunication().setUnits(duration);
        getOngoingCommunication().calcPrice(getClientType(), isFriend(receiver.getId()));
        getOngoingCommunication().setState("FINISHED");
        getState().revertState();
        receiver.getState().revertState();
        setOngoingCommunication(null);
        receiver.setOngoingCommunication(null);
    }

    public void sendTextCommunication(Network n, String receiverId, String message) throws 
                Cores_UnknownTerminalKeyException, Cores_UnavailableTerminalException {
        Terminal receiver = n.getTerminal(receiverId);
        if (receiver.getState().isOff()) {
            receiver.addClientToNotify(getClient());
            throw new Cores_UnavailableTerminalException(receiver.getId());
        }
        Communication c = new Text(this, receiver, message);
        c.calcPrice(getClientType(), isFriend(receiverId));
        n.addCommunication(c);
        setUsed();
        receiver.setUsed();
        addSentCommunication(c);
        receiver.addReceivedCommunication(c);
    }

    public void addFriend(Network n, String id) throws Cores_RepeatedFriendException, Cores_UnknownTerminalKeyException {
        if (getFriends().contains(n.getTerminal(id))) {
            throw new Cores_RepeatedFriendException(id);
        }
        _friends.put(id, n.getTerminal(id));
    }

    public void removeFriend(Network n, String id) throws Cores_UnknownFriendException, Cores_UnknownTerminalKeyException {
        if (!getFriends().contains(n.getTerminal(id))) {
            throw new Cores_UnknownFriendException(id);
        }
        _friends.remove(id);
    }

    public double getValuePaid() {
        double paid = 0;
        for (Communication c : getSentCommunications().values()) {
            if (c.isPaid()) {
                paid += c.getPrice();
            }
        }
        return paid;
    }

    public double getValueOwed() {
        double owed = 0;
        for (Communication c : getSentCommunications().values()) {
            if (!c.isPaid()) {
                owed += c.getPrice();
            }
        }
        return owed;
    }

    public boolean isValidCommunication(Communication c) {
        return c.getSender().getId().equals(getId()) && c.getState().equals("FINISHED") && !c.isPaid();
    }

    public void performPayment(Network n, int id) throws Cores_InvalidCommunicationException, Cores_UnknownCommunicationKeyException {
        Communication communication = n.getCommunication(id);
        if (!isValidCommunication(communication))
            throw new Cores_InvalidCommunicationException(id);
        communication.setPaid(true);
    }

    public double getBalance() {
        return getValuePaid() - getValueOwed();
    }

    /**
     * Checks if this terminal can end the current interactive communication.
        *
        * @return true if this terminal is busy (i.e., it has an active interactive communication) and
        *          it was the originator of this communication.
        **/
    public abstract boolean canEndCurrentCommunication(Communication c);

    /**
     * Checks if this terminal can start a new communication.
        *
        * @return true if this terminal is neither off neither busy, false otherwise.
        **/
    public abstract boolean canStartCommunication();

}
