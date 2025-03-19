package komeiji.back.websocket.protocol;


import komeiji.back.websocket.session.Session;

public interface WebSocketFrameProtocol<T> {

    T transform(Object data, Session session);
}
