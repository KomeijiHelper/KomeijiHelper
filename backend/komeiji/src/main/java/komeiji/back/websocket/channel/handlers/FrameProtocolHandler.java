package komeiji.back.websocket.channel.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import komeiji.back.websocket.channel.Attributes;
import komeiji.back.websocket.protocol.ProtocolUtils;
import komeiji.back.websocket.protocol.WebSocketFrameProtocol;
import komeiji.back.websocket.session.Session;

public class FrameProtocolHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        assert session != null;
        WebSocketFrame frame = (WebSocketFrame) msg;
        WebSocketFrameProtocol<?> protocol = ProtocolUtils.getProtocols(frame.getClass());
        Object parsedMsg = protocol.transform(frame,session);
        ctx.fireChannelRead(parsedMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage() + FrameProtocolHandler.class.getName());
        super.exceptionCaught(ctx, cause);
    }
}
