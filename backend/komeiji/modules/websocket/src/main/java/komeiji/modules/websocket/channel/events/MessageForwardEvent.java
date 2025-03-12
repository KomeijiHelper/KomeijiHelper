package komeiji.modules.websocket.channel.events;

import komeiji.modules.websocket.WebSocketServer;
import komeiji.modules.websocket.message.Message;
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
