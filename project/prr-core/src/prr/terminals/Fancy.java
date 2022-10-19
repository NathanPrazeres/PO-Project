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
    public boolean canStartCommunication() {
        return true;
    }

    @Override
    public boolean canEndCurrentCommunication() { return false; }
}
