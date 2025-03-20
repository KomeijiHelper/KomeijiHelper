package komeiji.back.websocket.message.fowardqueue;

import komeiji.back.websocket.message.Message;

// 专门用于聊天消息的转发，服务器和客户端之间的通信需主动发送
public interface MessageForwardQueue {
    void sendMessage();

    void registerMessage(Message message);
}
