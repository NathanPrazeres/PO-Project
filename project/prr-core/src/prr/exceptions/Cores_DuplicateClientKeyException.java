package prr.exceptions;

public class Cores_DuplicateClientKeyException extends Exception {

private static final long serialVersionUID = 202218101408L;

private final String key;

public Cores_DuplicateClientKeyException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}