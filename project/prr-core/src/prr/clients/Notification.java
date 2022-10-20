package prr.clients;

import prr.terminals.Terminal;

import java.io.Serializable;

public class Notification implements Serializable {
    private String _type;
    private Terminal _idTerminal;

    public Notification(String type, Terminal idTerminal) {
        _type = type;
        _idTerminal = idTerminal;
    }

    
}