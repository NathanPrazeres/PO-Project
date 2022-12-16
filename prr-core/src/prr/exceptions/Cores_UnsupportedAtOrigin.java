package prr.exceptions;

public class Cores_UnsupportedAtOrigin extends Exception {

    private static final long serialVersionUID = 202210201246L;

    private final String state;

    public Cores_UnsupportedAtOrigin(String state) {
        this.state = state;
    }

    public String getKey() {
        return state;
    }

}