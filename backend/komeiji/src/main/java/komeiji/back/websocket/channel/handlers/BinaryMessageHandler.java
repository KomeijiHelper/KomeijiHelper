package komeiji.back.websocket.channel.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import komeiji.back.websocket.channel.events.MessageForwardEvent;
import komeiji.back.websocket.message.impl.BinaryMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryMessageHandler extends SimpleChannelInboundHandler<BinaryMessage> {
    private static final Logger logger = LoggerFactory.getLogger(BinaryMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryMessage msg) throws Exception {

        logger.debug("handler text message: {}",msg);
        ctx.fireUserEventTriggered(new MessageForwardEvent(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage() + BinaryMessageHandler.class.getName());
        super.exceptionCaught(ctx, cause);
    }
}
