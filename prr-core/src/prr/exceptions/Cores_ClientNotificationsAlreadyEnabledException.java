package prr.exceptions;

public class Cores_ClientNotificationsAlreadyEnabledException extends Exception {

private static final long serialVersionUID = 202218101408L;

private final String key;

public Cores_ClientNotificationsAlreadyEnabledException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}