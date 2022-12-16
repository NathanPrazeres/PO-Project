package prr.exceptions;

public class Cores_InvalidCommunicationException extends Exception {

private static final long serialVersionUID = 202218101408L;

private final int key;

public Cores_InvalidCommunicationException(int key) {
    this.key = key;
}

public int getKey() {
    return key;
}

}