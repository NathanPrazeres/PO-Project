package prr.notifications;

import prr.terminals.Terminal;

public class B2I extends Notification {
    public B2I(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getTypeName() {
        return "B2I";
    }
}
