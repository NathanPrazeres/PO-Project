package prr.exceptions;

public class Cores_UnknownFriendException extends Exception {

    private static final long serialVersionUID = 202219101932L;

    private final String key;

    public Cores_UnknownFriendException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}