package prr.exceptions;

public class Cores_RepeatedFriendException extends Exception {

private static final long serialVersionUID = 202218101422L;

private final String key;

public Cores_RepeatedFriendException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}