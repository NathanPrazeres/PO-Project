package prr.terminals;


public class Basic extends Terminal {
    public Basic(String id) {
        super(id);
    }

    public boolean canEndCurrentCommunication() {
        if (this.getState().equals("Busy")) {
            return true;
        }
        return false;
    }

    public boolean canStartCommunication() {
        if (this.getState().equals("Idle") || this.getState().equals("Busy")) {
            return true;
        }
        return false;
    }
}