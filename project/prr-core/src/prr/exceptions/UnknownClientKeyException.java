package prr.exceptions;

public class UnknownClientKeyException extends Exception {

private static final long serialVersionUID = 202218101452L;

private final String key;

public UnknownClientKeyException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}