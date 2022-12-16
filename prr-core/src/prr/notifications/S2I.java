package prr.notifications;

import prr.terminals.Terminal;

public class S2I extends Notification {
    public S2I(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getTypeName() {
        return "S2I";
    }
}
