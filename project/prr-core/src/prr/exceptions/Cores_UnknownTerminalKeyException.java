package prr.exceptions;

public class Cores_UnknownTerminalKeyException extends Exception {

    private static final long serialVersionUID = 202219101932L;

    private final String key;

    public Cores_UnknownTerminalKeyException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
