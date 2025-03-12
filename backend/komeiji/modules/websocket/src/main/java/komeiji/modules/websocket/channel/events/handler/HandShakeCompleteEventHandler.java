package komeiji.modules.websocket.channel.events.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import komeiji.modules.websocket.WebSocketServer;
import komeiji.modules.websocket.channel.Attributes;
import komeiji.modules.websocket.session.OneWaySession;
import komeiji.modules.websocket.session.Session;


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
            Session session = new OneWaySession(uri,ctx.channel());
            ctx.channel().attr(Attributes.SESSION).set(session);
            WebSocketServer.getWebSocketSingleServer()
                    .getSessionManager().addSession(session);
        } catch(RuntimeException e) {
            ctx.fireExceptionCaught(e);
        }
    }
}
