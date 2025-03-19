package komeiji.back.websocket.channel.events.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.channel.Attributes;
import komeiji.back.websocket.session.Session;
import komeiji.back.websocket.session.SessionBuilder;


public class HandShakeCompleteEventHandler implements EventHandler {
    private WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = null;
    public HandShakeCompleteEventHandler(Object evt) {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete event){
            this.handshakeComplete = event;
        }
    }

    @Override
    public boolean valid(){
        return this.handshakeComplete != null;
    }

    @Override
    public void handlerEvent(ChannelHandlerContext ctx) {
        if(handshakeComplete == null){
            return;
        }
        String uri = handshakeComplete.requestUri();
        System.out.println(uri);
        try {
            Session session = SessionBuilder.createSession(uri,ctx.channel());
            ctx.channel().attr(Attributes.SESSION).set(session);
            WebSocketServer.getWebSocketSingleServer()
                    .getSessionManager().addSession(session);
        } catch(RuntimeException e) {
            ctx.fireExceptionCaught(e);
        }
    }
}
