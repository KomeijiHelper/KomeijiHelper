package komeiji.back;

import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.GlobalEventExecutor;
import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.message.fowardqueue.impl.CLMessageQueue;
import komeiji.back.websocket.session.SessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
        WebSocketServer webSocketServer = WebSocketServer.getWebSocketSingleServer(LogLevel.INFO,8192,"/ws",
                new SessionManager(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)),
                new CLMessageQueue());
        webSocketServer.startServer(54950);
    }
}
