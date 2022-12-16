package prr.exceptions;

public class Cores_UnknownCommunicationKeyException extends Exception {

private static final long serialVersionUID = 202218101452L;

private final int key;

public Cores_UnknownCommunicationKeyException(int key) {
    this.key = key;
}

public int getKey() {
    return key;
}

}