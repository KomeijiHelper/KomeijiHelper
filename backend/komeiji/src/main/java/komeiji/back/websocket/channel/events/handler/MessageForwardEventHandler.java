package komeiji.back.websocket.channel.events.handler;

import io.netty.channel.ChannelHandlerContext;
import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.channel.events.MessageForwardEvent;

public class MessageForwardEventHandler implements EventHandler {
    private MessageForwardEvent messageForwardEvent = null;
    public MessageForwardEventHandler(Object evt) {
        if (evt instanceof MessageForwardEvent event) {
            messageForwardEvent = event;
        }
    }

    @Override
    public boolean valid() {
        return messageForwardEvent != null;
    }

    @Override
    public void handlerEvent(ChannelHandlerContext ctx) {
        if (messageForwardEvent == null) {
            return;
        }
        System.out.println("trigger message forward event");
        WebSocketServer.getWebSocketSingleServer().getMessageForwardQueue().sendMessage();
    }
}
