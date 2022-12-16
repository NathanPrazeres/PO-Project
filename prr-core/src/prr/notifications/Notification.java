package prr.notifications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Notification implements Serializable {
    private Terminal _terminal;

    public Notification(Terminal terminal) {
        _terminal = terminal;
    }

    public Terminal getTerminal() { return _terminal; }

    public abstract String getTypeName();

    @Override
    public String toString() {
        return getTypeName() + "|" + _terminal.getId();
    }
}