package prr.terminals;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
    private List<Communication> _receivedCommunications = new ArrayList<>();
    private List<Communication> _sentCommunications = new ArrayList<>();
    private Tariff _tariff = new Normal();

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

    public Terminal(String id, String clientId) {
        _id = id;
        _clientId = clientId;
        paid.add(0);
        owed.add(0);
    }
 // order terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friendn
    public abstract String getType();

    public String getId() {
        return _id;
    }

    public String getState() {
        return _state;
    }

    public String getClientId() {
        return _clientId;
    }

    public String getBalacePaid() {
        int paid = 0;
        for (Integer i : _paid) {
            paid += i;
        }
        return String.valueOf(paid);
    }

    public String getBalaceOwed() {
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
    public abstract boolean canEndCurrentCommunication(Communication communication);

    /**
     * Checks if this terminal can start a new communication.
        *
        * @return true if this terminal is neither off neither busy, false otherwise.
        **/
    public abstract boolean canStartCommunication(String type);

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

    public void communicationEnded(int price, Tariff t) {
        _owed.add(price);
        this.updateBalance();
        if (t instanceof Gold) {
            if  (communication.getType() == "VIDEO") {
                t.inc();
                if (t.consecutive == 5) {
                    _tariff = new Platinum();
                }
            }
            _tariff.interrupt();
        }
        else if (_tariff instanceof Platinum) {
            if  (communication.getType() == "SMS") {
                _tariff.inc();
                if (_tariff.consecutive == 2) {
                    _tariff = new Gold();
                }
            }
            t.interrupt();
        }
        if (_balance < 0 && !(t instanceof Normal)) {
            _tariff = new Normal();
        }
    }

    public void addFriend(Terminal friend) {
        _friends.put(friend.getId(), friend);
    }

    public void removeFriend(String id) {
        _friends.remove(id);
    }

    public void startCommunication(String type, Terminal receiver) {
        if (canStartCommunication()) {
            busy();
            receiver.busy();
            Communication communication = new Communication(type, getId(), receiver.getId());
            _owed.add(communication.getPrice());
            _sentCommunications.add(communication);
            receiver.receivedCommunications.add(communication);
        }
    }

    public void endCurrentCommunication(Terminal receiver) {
        if (canEndCurrentCommunication()) {
            idle();
            receiver.idle();
        }
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
