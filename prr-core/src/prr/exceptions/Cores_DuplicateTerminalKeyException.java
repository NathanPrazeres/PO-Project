package prr.exceptions;

public class Cores_DuplicateTerminalKeyException extends Exception {

private static final long serialVersionUID = 202218101429L;

private final String key;

public Cores_DuplicateTerminalKeyException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}