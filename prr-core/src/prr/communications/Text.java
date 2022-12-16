package prr.communications;

import prr.terminals.Terminal;
import prr.clients.ClientType;

public class Text extends Communication {
    private String _message;

    public Text(Terminal sender, Terminal receiver, String message) {
        super(sender, receiver, message.length(), "FINISHED");
        _message = message;
    }

    @Override
    public void calcPrice(ClientType t, boolean isFriend) {
        double price = t.text(getUnits());
        if (isFriend) {
            setPrice(price/2);
        } else {
            setPrice(price);
        }
    }

    @Override
    public String toString() {
        return "TEXT|" + 
                getId() + "|" + 
                getSender() + "|" + 
                getReceiver() + "|" + 
                getUnits() + "|" + 
                Math.round(getPrice()) + "|" + 
                getState();
    }
}
