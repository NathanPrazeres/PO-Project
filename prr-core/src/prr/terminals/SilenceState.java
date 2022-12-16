package prr.terminals;

import prr.notifications.S2I;
import prr.exceptions.Cores_AlreadySilenceException;

public class SilenceState extends TerminalState {

    public SilenceState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getStateName() {
        return "SILENCE";
    }

    @Override
    public boolean isSilence() {
        return true;
    }

    @Override
    public void turnIdle() {
        getTerminal().setState(new IdleState(getTerminal()));
        getTerminal().sendNotifications(new S2I(getTerminal()));
    }

    @Override
    public void turnBusy() {
        TerminalState s = this;
        getTerminal().setState(new BusyState(getTerminal()));
        setPreviousState(s);
    }

    @Override
    public void turnSilence() throws Cores_AlreadySilenceException {
        throw new Cores_AlreadySilenceException();
    }

    @Override
    public void turnOff() {
        getTerminal().setState(new OffState(getTerminal()));
    }
}
