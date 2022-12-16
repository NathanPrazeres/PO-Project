package prr.terminals;

import prr.exceptions.Cores_AlreadyOnException;

public class IdleState extends TerminalState {
    public IdleState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getStateName() {
        return "IDLE";
    }

    @Override
    public boolean isIdle() {
        return true;
    }

    @Override
    public void turnIdle() throws Cores_AlreadyOnException {
        throw new Cores_AlreadyOnException();
    }

    @Override
    public void turnBusy() {
        TerminalState s = this;
        getTerminal().setState(new BusyState(getTerminal()));
        setPreviousState(s);
    }

    @Override
    public void turnSilence() {
        getTerminal().setState(new SilenceState(getTerminal()));
    }

    @Override
    public void turnOff() {
        TerminalState s = this;
        getTerminal().setState(new OffState(getTerminal()));
        setPreviousState(s);
    }
}