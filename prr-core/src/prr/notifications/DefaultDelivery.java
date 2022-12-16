package prr.notifications;

import prr.clients.Client;

public class DefaultDelivery implements NotificationDeliveryMethod {
    public void deliverNotification(Client client, Notification notification) {
        client.receiveNotification(notification);
    }
}
