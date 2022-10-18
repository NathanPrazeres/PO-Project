package prr.exceptions;

public class InvalidTerminalKeyException extends Exception {

private static final long serialVersionUID = 202218101422L;

private final String key;

public InvalidTerminalKeyException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}