package prr.terminals;

import prr.notifications.B2I;

public class BusyState extends TerminalState {
    public BusyState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getStateName() {
        return "BUSY";
    }   

    @Override
    public boolean isBusy() {
        return true;
    }

    @Override
    public void turnIdle() {
        getTerminal().setState(new IdleState(getTerminal()));
        getTerminal().sendNotifications(new B2I(getTerminal()));
    }

    @Override
    public void turnSilence() {
        getTerminal().setState(new SilenceState(getTerminal()));
    }
}
