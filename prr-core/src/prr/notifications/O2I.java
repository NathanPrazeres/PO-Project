package prr.notifications;

import prr.terminals.Terminal;

public class O2I extends Notification {
    public O2I(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getTypeName() {
        return "O2I";
    }
}
