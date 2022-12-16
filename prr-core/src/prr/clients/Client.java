package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.ClientType;
import prr.communications.Communication;
import prr.notifications.DefaultDelivery;
import prr.notifications.Notification;
import prr.notifications.NotificationDeliveryMethod;
import prr.terminals.Terminal;

public class Client implements Serializable {

    private static final long serialVersionUID = 202208091753L;

    private ClientType _type = new Normal(this);
    private String _id;
    private String _name;
    private int _nif;
    private Map<String, Terminal> _terminals = new TreeMap<>();
    private Map<Integer, Communication> _sentCommunications = new TreeMap<>();
    private Map<Integer, Communication> _receivedCommunications = new TreeMap<>();
    private List<Notification> _notifications = new ArrayList<>();
    private boolean _notifActive = true;
    private NotificationDeliveryMethod _delivery = new DefaultDelivery();


    public Client(String id, String name, int nif) {
        _id = id;
        _name = name;
        _nif = nif;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getNif() {
        return _nif;
    }

    public Collection<Terminal> getTerminals() {
        return _terminals.values();
    }

    public ClientType getType() {
        return _type;
    }

    public boolean getNotifActive() {
        return _notifActive;
    }

    public List<Notification> getNotifications() {
        return _notifications;
    }

    public NotificationDeliveryMethod getDeliveryMethod() {
        return _delivery;
    }

    public void receiveNotification(Notification notification) {
        if (getNotifActive()) {
            _notifications.add(notification);
        }
    }

    public Map<Integer, Communication> getSentCommunications() {
        return _sentCommunications;
    }

    public Map<Integer, Communication> getReceivedCommunications() {
        return _receivedCommunications;
    }

    public void addTerminal(Terminal t) {
        _terminals.put(t.getId(), t);
    }

    public void changeType(ClientType type) {
        _type = type;
    }

    public float totalPaid() {
        float paid = 0;
        for (Communication c : _sentCommunications.values()) {
            if (c.isPaid()) {
                paid += c.getPrice();
            }
        }
        return paid;
    }

    public float totalOwed() {
        float owed = 0;
        for (Communication c : _sentCommunications.values()) {
            if (!c.isPaid()) {
                owed += c.getPrice();
            }
        }
        return owed;
    }

    public boolean hasDebt() {
        for (Communication c : _sentCommunications.values()) {
            if (!c.isPaid()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CLIENT" + "|" + 
                getId() + "|" +
                getName() + "|" +
                getNif() + "|" +
                getType() + "|" +
                (getNotifActive() ? "YES" : "NO") + "|" +
                getTerminals().size() + "|" +
                Math.round(totalPaid()) + "|" +
                Math.round(totalOwed());
    }

    public String readNotifications() {
        StringBuilder notifications = new StringBuilder("\n");
        for (Notification n : getNotifications()) {
            notifications.append(n.toString());
            notifications.append("\n");
        }
        notifications.deleteCharAt(notifications.length() - 1);
        getNotifications().clear();
        return notifications.toString();
    }
}