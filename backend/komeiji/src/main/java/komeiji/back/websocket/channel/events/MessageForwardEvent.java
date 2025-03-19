package komeiji.back.websocket.channel.events;

import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.message.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageForwardEvent {
    private Message message;

    public MessageForwardEvent(Message message) {
        this.message = message;
        forwardMessage(message);
    }

    private static void forwardMessage(Message message) {
        WebSocketServer.getWebSocketSingleServer().getMessageForwardQueue().registerMessage(message);
    }
}
