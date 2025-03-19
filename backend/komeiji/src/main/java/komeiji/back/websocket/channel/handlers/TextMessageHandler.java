package komeiji.back.websocket.channel.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import komeiji.back.websocket.channel.events.MessageForwardEvent;
import komeiji.back.websocket.message.impl.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextMessageHandler extends SimpleChannelInboundHandler<TextMessage> {
    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextMessage msg) {
        logger.debug("handler text message: {}",msg);
        ctx.fireUserEventTriggered(new MessageForwardEvent(msg));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close(); //关闭连接
    }
}