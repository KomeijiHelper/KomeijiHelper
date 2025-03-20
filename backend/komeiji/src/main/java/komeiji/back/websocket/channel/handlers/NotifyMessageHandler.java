package komeiji.back.websocket.channel.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import komeiji.back.websocket.channel.events.MessageForwardEvent;
import komeiji.back.websocket.message.impl.NotifyMessage;

public class NotifyMessageHandler extends SimpleChannelInboundHandler<NotifyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NotifyMessage msg) throws Exception {
        // TODO: handle msg
        ctx.fireUserEventTriggered(new MessageForwardEvent(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage() + NotifyMessageHandler.class.getName());
        super.exceptionCaught(ctx, cause);
    }
}
