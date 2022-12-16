package prr.notifications;

import prr.clients.Client;

public interface NotificationDeliveryMethod {
    public void deliverNotification(Client client, Notification notification);
}
