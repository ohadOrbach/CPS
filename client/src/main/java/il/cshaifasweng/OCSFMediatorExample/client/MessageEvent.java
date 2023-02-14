package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class MessageEvent {
    private Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }

    public MessageEvent(String message) {
        assert this.message != null;
        this.message.setMessage(message);
    }

    public Message getMessage() {
        return message;
    }
}
