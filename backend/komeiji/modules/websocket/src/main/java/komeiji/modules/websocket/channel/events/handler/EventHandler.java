package komeiji.modules.websocket.channel.events.handler;

import io.netty.channel.ChannelHandlerContext;

public interface EventHandler {

    boolean valid();

    void handlerEvent(ChannelHandlerContext ctx);
}
