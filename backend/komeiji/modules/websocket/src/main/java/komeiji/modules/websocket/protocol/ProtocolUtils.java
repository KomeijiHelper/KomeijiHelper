package komeiji.modules.websocket.protocol;

import io.netty.handler.codec.http.websocketx.*;
import komeiji.modules.websocket.protocol.impl.DefaultBinaryFrameProtocol;
import komeiji.modules.websocket.protocol.impl.DefaultTextFrameProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ProtocolUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolUtils.class);

    static class ProtocolEntity {
        boolean defaulted;
        WebSocketFrameProtocol<?> protocol;

        ProtocolEntity(WebSocketFrameProtocol<?> protocol) {
            this.protocol = protocol;
            this.defaulted = false;
        }
    }

    private static HashMap<Class<? extends WebSocketFrame>,ProtocolEntity> protocols = null;

    private static HashMap<Class<? extends WebSocketFrame>,ProtocolEntity> protocols(){
        if(protocols == null) {
            protocols = new HashMap<>(Map.of(
                    TextWebSocketFrame.class, new ProtocolEntity(new DefaultTextFrameProtocol()),
                    BinaryWebSocketFrame.class, new ProtocolEntity(new DefaultBinaryFrameProtocol())
            ));
        }
        return protocols;
    }


    public static WebSocketFrameProtocol<?> getProtocols(Class<? extends WebSocketFrame> clazz) {
        ProtocolEntity entity = protocols().get(clazz);
        return entity.protocol;
    }


    public static void registerProtocol(WebSocketFrameProtocol<?> protocol, Class<? extends WebSocketFrame> clazz) {
        ProtocolEntity entity = protocols().get(clazz);
        if(entity.defaulted) {
            logger.error("Protocol has already been registered for {}", clazz.getSimpleName());
            System.exit(1);
        }
        entity.protocol = protocol;
    }

    public static void unregisterProtocol(Class<? extends WebSocketFrame> clazz) {
        ProtocolEntity entity = protocols().get(clazz);
        if(!entity.defaulted) {
            logger.error("Protocol has not been registered for {} yet", clazz.getSimpleName());
            System.exit(1);
        }
        entity.defaulted = false;
        entity.protocol = null;
    }
}
