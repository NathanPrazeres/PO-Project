package prr.terminals;

import java.io.Serializable;

import prr.exceptions.Cores_AlreadyOffException;
import prr.exceptions.Cores_AlreadyOnException;
import prr.exceptions.Cores_AlreadySilenceException;

public abstract class TerminalState implements Serializable {
    
    private static final long serialVersionUID = 202208091753L;

    private TerminalState _previousState;
    private Terminal _terminal;

    public TerminalState(Terminal terminal) {
        _terminal = terminal;
    }

    public Terminal getTerminal() {
        return _terminal;
    }

    public void setPreviousState(TerminalState s) {
        _previousState = s;
    }

    public void revertState() {
        getTerminal().setState(_previousState);
    }

    public abstract String getStateName();

    public boolean isIdle() { return false; }
    public boolean isBusy() { return false; }
    public boolean isSilence() { return false; }
    public boolean isOff() { return false; }

    public void turnIdle() throws Cores_AlreadyOnException {}
    public void turnBusy() {}
    public void turnSilence() throws Cores_AlreadySilenceException {}
    public void turnOff() throws Cores_AlreadyOffException {}
}
