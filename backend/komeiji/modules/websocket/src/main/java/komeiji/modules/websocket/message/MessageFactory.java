package komeiji.modules.websocket.message;


import komeiji.modules.websocket.message.impl.TextMessage;
import komeiji.modules.websocket.session.SessionToken;

public class MessageFactory {
    public static TextMessage newTextMessage(MessageType type, SessionToken from, SessionToken to, String data) {
        return switch (type) {
            case TEXT_MESSAGE -> new TextMessage(from,to,data);
            default -> throw new IllegalArgumentException("Unknown message type");
        };
    }
}
