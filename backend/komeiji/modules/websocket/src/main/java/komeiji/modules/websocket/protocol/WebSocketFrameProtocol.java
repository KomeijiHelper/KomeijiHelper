package komeiji.modules.websocket.protocol;


import komeiji.modules.websocket.session.Session;

public interface WebSocketFrameProtocol<T> {

    T transform(Object data, Session session);
}
