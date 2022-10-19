package prr.exceptions;

public class Cores_UnknownClientKeyException extends Exception {

private static final long serialVersionUID = 202218101452L;

private final String key;

public Cores_UnknownClientKeyException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}