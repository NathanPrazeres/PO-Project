package prr.terminals;

import prr.exceptions.Cores_AlreadyOffException;
import prr.notifications.O2I;
import prr.notifications.O2S;

public class OffState extends TerminalState {
    public OffState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getStateName() {
        return "OFF";
    }

    @Override
    public boolean isOff() {
        return true;
    }

    @Override
    public void turnIdle() {
        getTerminal().setState(new IdleState(getTerminal()));
        getTerminal().sendNotifications(new O2I(getTerminal()));
    }

    @Override
    public void turnSilence() {
        getTerminal().setState(new SilenceState(getTerminal()));
        getTerminal().sendNotifications(new O2S(getTerminal()));
    }

    @Override
    public void turnOff() throws Cores_AlreadyOffException {
        throw new Cores_AlreadyOffException();
    }
}
