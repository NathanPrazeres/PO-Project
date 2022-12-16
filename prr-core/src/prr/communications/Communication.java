package prr.communications;

import java.io.Serializable;

import prr.clients.ClientType;
import prr.terminals.Terminal;

public abstract class Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;

    private int _id;
    private Terminal _sender;
    private Terminal _receiver;
    private String _state;
    private int _units;
    private double _price = 0;
    private static int _idCounter = 1;
    private boolean _paid = false;

    public Communication(Terminal sender, Terminal receiver, int units, String state) {
        _id = _idCounter++;
        _sender = sender;
        _receiver = receiver;
        _units = units;
        _state = state;
    }

    public void setUnits(int units) { _units = units; }
    public void setPrice(double price) { _price = price; }
    public void setState(String state) { _state = state; }
    public void setPaid(boolean paid) { _paid = paid; }

    public int getId() { return _id; }
    public Terminal getSender() { return _sender; }
    public Terminal getReceiver() { return _receiver; }
    public String getState() { return _state; }
    public int getUnits() { return _units; }
    public double getPrice() { return _price; }
    public boolean isPaid() { return _paid; }

    public abstract void calcPrice(ClientType t, boolean isFriend);
}
