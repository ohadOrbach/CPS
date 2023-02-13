package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ErrorEvent {
    private Message message;

    public ErrorEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
