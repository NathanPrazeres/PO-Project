package prr.terminals;

public class Fancy extends Terminal{
    
    public Fancy(String id) {
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
