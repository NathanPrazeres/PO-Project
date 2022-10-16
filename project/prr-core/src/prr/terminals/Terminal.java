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

    private String _state = "IDLE";
    private String _id;
    private int _balance = 0;
    private Map<String, Terminal> _friends = new TreeMap<>();
    private List<Integer> _paid = new ArrayList<>();
    private List<Integer> _owed = new ArrayList<>();
    private List<Communication> _receivedCommunications = new ArrayList<>();
    private List<Communication> _sentCommunications = new ArrayList<>();

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

    public Terminal(String id) {
        _id = id;
        paid.add(0);
        owed.add(0);
    }



    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public String getId() {
        return _id;
    }
    
    /**
     * Checks if this terminal can end the current interactive communication.
        *
        * @return true if this terminal is busy (i.e., it has an active interactive communication) and
        *          it was the originator of this communication.
        **/
    public abstract boolean canEndCurrentCommunication();

    /**
     * Checks if this terminal can start a new communication.
        *
        * @return true if this terminal is neither off neither busy, false otherwise.
        **/
    public abstract boolean canStartCommunication();

    public void updateBalance() {
        int newBalance = 0;
        for (Integer p: paid) {
            newBalance += p;
        }
        for (Integer o: owed) {
            newBalance -= o;
        }
        _balance = newBalance;
    }
    
    public int getBalance() {
        return _balance;
    }

    public Tariff communicationEnded(int price, Tariff t) {
        _owed.add(price);
        this.updateBalance();
        if (_balance < 0 && !(t instanceof Normal)) {
            t = new Normal();
        }
        if (t instanceof Gold) {
            if  (communication.type == "VIDEO") {
                t.inc();
                if (t.consecutive == 5) {
                    _tariff = new Platinum();
                }
            }
            _tariff.interrupt();
        }
        else if (_tariff instanceof Platinum) {
            if  (communication.type == "SMS") {
                _tariff.inc();
                if (_tariff.consecutive == 2) {
                    _tariff = new Gold();
                }
            }
            t.interrupt();
        }
    }

    public void addFriend(Terminal friend) {
        _friends.put(friend.getId(), friend);
    }

    public void removeFriend(String id) {
        _friends.remove(id);
    }

    public void startCommunication(String type, Terminal receiver) {
        if (this.canStartCommunication()) {
            this.setState("BUSY");
            receiver.setState("BUSY");
            Communication communication = new Communication(type, this.getId(), receiver.getId());
            _owed.add(communication.getPrice());
            _sentCommunications.add(communication);
            receiver.receivedCommunications.add(communication);
        }
    }

    public void endCurrentCommunication() {
        if (this.canEndCurrentCommunication()) {
            this.setState("IDLE");
            for (Communication c: _receivedCommunications) {
                if (c.getState() == "ACTIVE") {
                    c.setState("ENDED");
                    c.getSender().communicationEnded(c.getPrice(), _tariff);
                }
            }
        }
    }

    public void turnOff() {
        this.setState("OFF");
    }

    public void turnOn() {
        this.setState("IDLE");
    }
}
