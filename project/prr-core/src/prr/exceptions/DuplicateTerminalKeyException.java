package prr.exceptions;

public class DuplicateTerminalKeyException extends Exception {

private static final long serialVersionUID = 202218101429L;

private final String key;

public DuplicateTerminalKeyException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}