package prr.exceptions;

public class Cores_DestinationIsBusy extends Exception {

    private static final long serialVersionUID = 202210201246L;

    private final String state;

    public Cores_DestinationIsBusy(String state) {
        this.state = state;
    }

    public String getKey() {
        return state;
    }

}