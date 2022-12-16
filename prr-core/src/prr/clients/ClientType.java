package prr.clients;

import java.io.Serializable;

import prr.tariff.Tariff;

public abstract class ClientType implements Serializable {
    private Client _client;
    private Tariff _tariff;
    private boolean _ready = false;
    private int _consecutive = 0;

    public ClientType(Client client, Tariff tariff) {
        _client = client;
        _tariff = tariff;
    }

    public Tariff getTariff() {
        return _tariff;
    }

    public abstract String getType();
    public abstract double text(int n);
    public abstract double voice(int n);
    public abstract double video(int n);


    
    public void addCons() { _consecutive += 1; }
    public void resetCons() { _consecutive = 0; }
    public int getCons() { return _consecutive; }
    public boolean isReady() { return _ready; }
    public void setReady(boolean ready) { _ready = ready; }
}
