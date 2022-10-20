package prr.terminals;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private String _id;
    private String _clientId;
    private String _state = "IDLE";
    private int _balance = 0;
    private Map<String, Terminal> _friends = new TreeMap<>();
    private List<Integer> _paid = new ArrayList<>();
    private List<Integer> _owed = new ArrayList<>();

    private boolean _used = false;
    /* private List<Communication> _receivedCommunications = new ArrayList<>();
    private List<Communication> _sentCommunications = new ArrayList<>(); */

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

    public Terminal(String id, String clientId) {
        _id = id;
        _clientId = clientId;
        _paid.add(0);
        _owed.add(0);
    }
 // order terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friendn
    public abstract String getType();

    @Override
    public String toString() {
        return "TERMINAL" + "|" + getType() + "|" + getId() + "|" + getClientId() + "|" +
            getState() + "|" + getBalancePaid() + "|" + getBalanceOwed();
    }
    public String getId() {
        return _id;
    }

    public String getState() {
        return _state;
    }

    public String getClientId() {
        return _clientId;
    }

    public Collection<Terminal> getFriends() {
        return _friends.values();
    }

    public boolean getUsed() { return _used; }

    public void addFriend(Terminal friend) {
        _friends.put(friend.getId(), friend);
    }

    public void removeFriend(String id) {
        _friends.remove(id);
    }

    public String getBalancePaid() {
        int paid = 0;
        for (Integer i : _paid) {
            paid += i;
        }
        return String.valueOf(paid);
    }

    public String getBalanceOwed() {
        int owed = 0;
        for (Integer i : _owed) {
            owed += i;
        }
        return String.valueOf(owed);
    }

    public void setState(String state) {
        _state = state;
    }

    
    /**
     * Checks if this terminal can end the current interactive communication.
        *
        * @return true if this terminal is busy (i.e., it has an active interactive communication) and
        *          it was the originator of this communication.
        **/
    //public abstract boolean canEndCurrentCommunication(Communication communication);

    /**
     * Checks if this terminal can start a new communication.
        *
        * @return true if this terminal is neither off neither busy, false otherwise.
        **/
    public abstract boolean canStartCommunication();

    public abstract boolean canEndCurrentCommunication();

    public void updateBalance() {
        int newBalance = 0;
        for (Integer p: _paid) {
            newBalance += p;
        }
        for (Integer o: _owed) {
            newBalance -= o;
        }
        _balance = newBalance;
    }
    
    public int getBalance() {
        return _balance;
    }


    public void off() {
        setState("OFF");
    }

    public void idle() {
        setState("IDLE");
    }

    public void silence() {
        setState("SILENCE");
    }

    public void busy() {
        setState("BUSY");
    }

}
