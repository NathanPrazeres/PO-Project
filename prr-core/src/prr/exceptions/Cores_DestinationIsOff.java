package prr.exceptions;

public class Cores_DestinationIsOff extends Exception {

    private static final long serialVersionUID = 202210201246L;

    private final String state;

    public Cores_DestinationIsOff(String state) {
        this.state = state;
    }

    public String getKey() {
        return state;
    }

}