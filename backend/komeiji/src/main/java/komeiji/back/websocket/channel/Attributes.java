package komeiji.back.websocket.channel;

import io.netty.util.AttributeKey;
import komeiji.back.websocket.session.Session;

public class Attributes {
    public static final AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
