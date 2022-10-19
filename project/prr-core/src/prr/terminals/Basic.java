package prr.terminals;

public class Basic extends Terminal {

    public Basic(String id, String idClient) {
        super(id, idClient);
    }

    @Override
    public String getType() {
        return "BASIC";
    }

    @Override
    public boolean canStartCommunication() {
        return true;
    }

    @Override
    public boolean canEndCurrentCommunication() { return false; }
}