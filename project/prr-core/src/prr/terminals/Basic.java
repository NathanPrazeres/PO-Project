package prr.terminals;

public class Basic extends Terminal {

    public Basic(String id, String state) {
        super(id, idClient);
    }

    @Override
    public String getType() {
        return "BASIC";
    }

    @Override
    public boolean canEndCurrentCommunication(Communication communication) {
        if (this.getState().equals("BUSY") && communication.getOriginator().equals(this.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        if (this.getState().equals("IDLE") || this.getState().equals("BUSY")) {
            return true;
        }
        return false;
    }
}