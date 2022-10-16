package prr.clients;

import java.io.Serializable;

import prr.terminals.Communication;
import prr.terminals.Terminal;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import prr.tariff.Tariff;
import prr.tariff.Normal;
import prr.tariff.Gold;
import prr.tariff.Platinum;

public class Client implements Serializable {

    private static final long serialVersionUID = 202208091753L;

    private Tariff _tariff = new Normal();
    private String _id;
    private String _name;
    private int _nif;
    private Map<String, Terminal> _terminals = new TreeMap<>();
    private List<Integer> _paid = new ArrayList<>();
    private List<Integer> _owed = new ArrayList<>();
    private List<Communication> _sentCommunications = new ArrayList<>();
    private List<Communication> _receivedCommunications = new ArrayList<>();
    private List<Notification> _notifications = new ArrayList<>();
    private int _balance = 0;


    public Client(String id, String name, int nif) {
        _id = id;
        _name = name;
        _nif = nif;
    }


    public void changeTariff(Tariff newTariff) {
        _tariff = newTariff;
    }

    public void addPaid(int price) {
        _paid.add(price);
        _balance += price;
    }

    public void addOwed(int price) {
        _owed.add(price);
        _balance -= price;
    }

    public void readNotifications() {
        // show notifications;
        // notifications.clear();
    }
}