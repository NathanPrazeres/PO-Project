package prr.terminals;

public class Fancy extends Terminal {
    
    public Fancy(String id, String idClient) {
        super(id, idClient);
    }

    @Override
    public String getType() {
        return "FANCY";
    }

    @Override
    public boolean canEndCurrentCommunication(/* Communication communication */) {
        /* if (this.getState().equals("BUSY") && communication.getOriginator().equals(this.getId())) {
            return true;
        } */
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        /* if (this.getState().equals("IDLE") || this.getState().equals("BUSY")) {
            return true;
        } */
        return false;
    }
}
