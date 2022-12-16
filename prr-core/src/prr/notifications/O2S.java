package prr.notifications;

import prr.terminals.Terminal;

public class O2S extends Notification {
    public O2S(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getTypeName() {
        return "O2S";
    }
}
