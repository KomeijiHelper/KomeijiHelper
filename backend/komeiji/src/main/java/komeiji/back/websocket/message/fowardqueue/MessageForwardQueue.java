package komeiji.back.websocket.message.fowardqueue;

import komeiji.back.websocket.message.Message;

public interface MessageForwardQueue {
    void sendMessage();

    void registerMessage(Message message);
}
