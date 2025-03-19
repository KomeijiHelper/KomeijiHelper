package komeiji.back.websocket.protocol.impl;


import komeiji.back.websocket.protocol.BinaryFrameTProtocol;
import komeiji.back.websocket.session.Session;

public class DefaultBinaryFrameProtocol extends BinaryFrameTProtocol<Object> {
    @Override
    public Object transform(Object data, Session session) {
        return null;
    }
}
