package komeiji.modules.websocket.message.fowardqueue.impl;

import komeiji.modules.websocket.message.Message;
import komeiji.modules.websocket.message.fowardqueue.MessageForwardQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

// ConcurrentLinkedQueue Implementation
public class CLMessageQueue implements MessageForwardQueue {

    private static final Logger logger = LoggerFactory.getLogger(CLMessageQueue.class);

    private final ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<>();

    public CLMessageQueue() {}

    @Override
    public void sendMessage(Message message) {
        Message msg = queue.poll();
        if (msg == null) {
            return;
        }
        // TODO: send message really
        System.out.println("sendMessage called: \n"+msg);
    }

    @Override
    public void registerMessage(Message message) {
        System.out.println("register message: \n" + message);
        queue.offer(message);
    }
}
