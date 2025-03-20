package komeiji.back.websocket.message.fowardqueue.impl;

import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.message.Message;
import komeiji.back.websocket.message.fowardqueue.MessageForwardQueue;
import komeiji.back.websocket.persistence.ConversationManager;
import komeiji.back.websocket.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

// ConcurrentLinkedQueue Implementation
public class CLMessageQueue implements MessageForwardQueue {

    private static final Logger logger = LoggerFactory.getLogger(CLMessageQueue.class);

    private final ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<>();

    private final ConversationManager conversationManager = new ConversationManager();

    public CLMessageQueue() {}

    @Override
    public void sendMessage() {
        if (queue.isEmpty()) {
            return;
        }
        Message msg = queue.poll();
        logger.debug("sendMessage called: {}",msg);
        Session targetSession = WebSocketServer.getWebSocketSingleServer().getSessionManager().findSession(msg.getTo());
        if( targetSession == null ) {
            logger.warn("target of message {} is null", msg);
            return;
        }
        if( !targetSession.getConnect().isWritable()) {
            logger.warn("channel of target {} is not writable", targetSession);
            return;
        }
        conversationManager.addMessageRecord(msg);
        Object content = msg.messageDecode();
        targetSession.getConnect().writeAndFlush(content);
    }

    @Override
    public void registerMessage(Message message) {
        System.out.println("register message: \n" + message);
        queue.offer(message);
    }
}
