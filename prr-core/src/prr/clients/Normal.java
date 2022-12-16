package prr.clients;

import prr.clients.Client;
import prr.clients.ClientType;
import prr.tariff.NormalTariff;

public class Normal extends ClientType {
    public Normal(Client client) {
        super(client, new NormalTariff());
    }

    @Override
    public String getType() {
        return "NORMAL";
    }

    public double text(int n) {
        return getTariff().text(n);
    }

    public double voice(int n) {
        return getTariff().voice(n);
    }

    public double video(int n) {
        return getTariff().video(n);
    }
}
