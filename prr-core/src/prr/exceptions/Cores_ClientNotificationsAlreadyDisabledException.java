package prr.exceptions;

public class Cores_ClientNotificationsAlreadyDisabledException extends Exception {

private static final long serialVersionUID = 202218101408L;

private final String key;

public Cores_ClientNotificationsAlreadyDisabledException(String key) {
    this.key = key;
}

public String getKey() {
    return key;
}

}