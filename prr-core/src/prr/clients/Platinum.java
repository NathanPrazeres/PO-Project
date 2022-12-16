package prr.clients;

import prr.clients.Client;
import prr.clients.ClientType;
import prr.tariff.PlatinumTariff;

public class Platinum extends ClientType {
    public Platinum(Client client) {
        super(client, new PlatinumTariff());
    }

    @Override
    public String getType() {
        return "PLATINUM";
    }

    public double text(int n) {
        addCons();
        if (getCons() == 2) {
            setReady(true);
        }
        return getTariff().text(n);
    }

    public double voice(int n) {
        resetCons();
        return getTariff().voice(n);
    }

    public double video(int n) {
        resetCons();
        return getTariff().video(n);
    }
}
