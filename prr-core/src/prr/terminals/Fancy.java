package prr.terminals;

import prr.clients.Client;
import prr.communications.Communication;

public class Fancy extends Terminal {
    public Fancy(String id, Client client) {
        super(id, client);
    }

    @Override
    public String typeToString() {
        return "FANCY";
    }

    @Override
    public boolean isFancy() { return true; }

    @Override
    public boolean canStartCommunication() {
        if (getState().isIdle() || getState().isSilence()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canEndCurrentCommunication(Communication c) {
        if (getState().isBusy() && c.getSender().getId().equals(getId())) {
            return true;
        }
        return false;
    }
}