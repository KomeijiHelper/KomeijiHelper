package komeiji.modules.websocket.channel;

import io.netty.util.AttributeKey;
import komeiji.modules.websocket.session.Session;

public class Attributes {
    public static final AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
