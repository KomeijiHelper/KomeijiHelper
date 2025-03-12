package komeiji.modules.websocket;

import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.GlobalEventExecutor;
import komeiji.modules.websocket.message.fowardqueue.impl.CLMessageQueue;
import komeiji.modules.websocket.session.SessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
        WebSocketServer chatServer = WebSocketServer.getWebSocketSingleServer(LogLevel.INFO,8192,"/chat",
                new SessionManager(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)),
                new CLMessageQueue());
        chatServer.startServer(54950);
    }

}
