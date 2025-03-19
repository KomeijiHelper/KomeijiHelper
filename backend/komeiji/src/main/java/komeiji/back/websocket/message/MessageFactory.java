package komeiji.back.websocket.message;


import komeiji.back.websocket.message.impl.TextMessage;
import komeiji.back.websocket.session.SessionToken;

public class MessageFactory {
    public static TextMessage newTextMessage(MessageType type, SessionToken from, SessionToken to, String data) {
        return switch (type) {
            case TEXT_MESSAGE -> new TextMessage(from,to,data);
            default -> throw new IllegalArgumentException("Unknown message type");
        };
    }
}
