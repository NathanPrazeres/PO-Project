package prr.clients;

import prr.terminals.Terminal;

public class Notification {
    private String _type;
    private Terminal _idTerminal;

    public Notification(String type, Terminal idTerminal) {
        this.type = type;
        this.idTerminal = idTerminal;
    }

    
}
