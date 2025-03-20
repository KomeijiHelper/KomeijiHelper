package komeiji.back.websocket.message.impl;


import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import komeiji.back.websocket.session.SessionToken;

public class NotifyMessage extends TextMessage {

    public NotifyMessage(SessionToken from, SessionToken to) {
        super(from, to);
    }

    public NotifyMessage(SessionToken from, SessionToken to, String text) {
        this(from, to);
        this.data = text;
    }

    @Override
    public TextWebSocketFrame messageDecode() {
        // TODO:
        return super.messageDecode();
    }
}
