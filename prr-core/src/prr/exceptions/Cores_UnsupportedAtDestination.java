package prr.exceptions;

public class Cores_UnsupportedAtDestination extends Exception {

    private static final long serialVersionUID = 202210201246L;

    private final String state;

    public Cores_UnsupportedAtDestination(String state) {
        this.state = state;
    }

    public String getKey() {
        return state;
    }

}