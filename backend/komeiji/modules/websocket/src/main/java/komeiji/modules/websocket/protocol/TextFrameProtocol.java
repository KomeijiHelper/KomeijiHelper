package komeiji.modules.websocket.protocol;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import komeiji.modules.websocket.session.Session;


public abstract class TextFrameProtocol<T> implements WebSocketFrameProtocol<T>{

    protected abstract T frameTransform(TextWebSocketFrame frame, Session session);

    @Override
    public T transform(Object data,Session session) throws ClassCastException {
        if(!(data instanceof TextWebSocketFrame frame)) {
            throw new ClassCastException();
        }
        return frameTransform(frame,session);
    }
}
