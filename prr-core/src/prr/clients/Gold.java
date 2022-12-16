package prr.clients;

import prr.clients.Client;
import prr.clients.ClientType;
import prr.tariff.GoldTariff;

public class Gold extends ClientType {
    public Gold(Client client) {
        super(client, new GoldTariff());
    }

    @Override
    public String getType() {
        return "GOLD";
    }
    
    public double text(int n) {
        resetCons();
        return getTariff().text(n);
    }

    public double voice(int n) {
        resetCons();
        return getTariff().voice(n);
    }

    public double video(int n) {
        addCons();
        if (getCons() == 5) {
            setReady(true);
        }
        return getTariff().video(n);
    }
}
