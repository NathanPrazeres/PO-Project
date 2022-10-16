package prr.clients;

import prr.terminals.Terminal;

public class Notification {
    private String type;
    private Terminal idTerminal;

    public Notification(String type, Terminal idTerminal) {
        this.type = type;
        this.idTerminal = idTerminal;
    }

    
}
