package prr.communications;

import prr.terminals.Terminal;
import prr.clients.ClientType;

public class Voice extends Communication {

    public Voice(Terminal sender, Terminal receiver) {
        super(sender, receiver, 0, "ONGOING");
    }

    @Override
    public void calcPrice(ClientType t, boolean isFriend) {
        double price = t.voice(getUnits());
        if (isFriend) {
            setPrice(price/2);
        } else {
            setPrice(price);
        }
    }

    @Override
    public String toString() {
        return "VOICE|" + 
                getId() + "|" + 
                getSender() + "|" + 
                getReceiver() + "|" + 
                getUnits() + "|" + 
                Math.round(getPrice()) + "|" + 
                getState();
    }
}
