package komeiji.modules.websocket.channel.handlers;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import komeiji.modules.websocket.WebSocketServer;
import komeiji.modules.websocket.channel.Attributes;
import komeiji.modules.websocket.channel.events.handler.EventHandler;
import komeiji.modules.websocket.channel.events.handler.HandShakeCompleteEventHandler;
import komeiji.modules.websocket.protocol.ProtocolUtils;
import komeiji.modules.websocket.protocol.WebSocketFrameProtocol;
import komeiji.modules.websocket.session.Session;

public class WebSocketConnectHandler extends ChannelInboundHandlerAdapter {

    private Session session = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // disconnect
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        System.out.println("channelUnregistered from: " + session.getId());
        WebSocketServer.getWebSocketSingleServer().getSessionManager().removeSession(session);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        assert session != null;
        WebSocketFrame frame = (WebSocketFrame) msg;
        WebSocketFrameProtocol<?> protocol = ProtocolUtils.getProtocols(frame.getClass());
        Object parsedMsg = protocol.transform(frame,session);
        ctx.fireChannelRead(parsedMsg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // connect
        EventHandler eventHandler = new HandShakeCompleteEventHandler(evt);
        if(eventHandler.valid() && session == null) {
            eventHandler.handlerEvent(ctx);
            session = ctx.channel().attr(Attributes.SESSION).get();
        }
        super.userEventTriggered(ctx, evt);
    }



}
