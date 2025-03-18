package komeiji.modules.websocket.protocol.impl;


import komeiji.modules.websocket.protocol.BinaryFrameTProtocol;
import komeiji.modules.websocket.session.Session;

public class DefaultBinaryFrameProtocol extends BinaryFrameTProtocol<Object> {
    @Override
    public Object transform(Object data, Session session) {
        return null;
    }
}
