package prr.communications;

import prr.terminals.Terminal;
import prr.clients.ClientType;

public class Video extends Communication {

    public Video(Terminal sender, Terminal receiver) {
        super(sender, receiver, 0, "ONGOING");
    }

    @Override
    public void calcPrice(ClientType t, boolean isFriend) {
        double price = t.video(getUnits());
        if (isFriend) {
            setPrice(price/2);
        } else {
            setPrice(price);
        }
    }

    @Override
    public String toString() {
        return "VIDEO|" + 
                getId() + "|" + 
                getSender() + "|" + 
                getReceiver() + "|" + 
                getUnits() + "|" + 
                Math.round(getPrice()) + "|" + 
                getState();
    }
}
