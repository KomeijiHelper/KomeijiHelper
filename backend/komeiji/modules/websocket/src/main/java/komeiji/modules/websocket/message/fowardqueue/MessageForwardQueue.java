package komeiji.modules.websocket.message.fowardqueue;

import komeiji.modules.websocket.message.Message;

public interface MessageForwardQueue {
    void sendMessage(Message message);

    void registerMessage(Message message);
}
