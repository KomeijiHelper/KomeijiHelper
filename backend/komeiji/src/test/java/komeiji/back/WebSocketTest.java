package komeiji.back;

import com.google.gson.JsonObject;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.util.concurrent.GlobalEventExecutor;
import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.message.fowardqueue.impl.CLMessageQueue;
import komeiji.back.websocket.session.SessionManager;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class WebSocketTest {

    private static final int port = 55480;
    private static final WebSocketServer webSocketServer = WebSocketServer.getWebSocketSingleServer(LogLevel.INFO,8192,"/ws",
            new SessionManager(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)),
            new CLMessageQueue());

    static {
        // start server
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable serverTask = () -> {
            webSocketServer.startServer(port);
        };

        executorService.submit(serverTask);
        executorService.shutdown();
    }



    @Test
    void testUser() {
        OkHttpClient client = new OkHttpClient();
        Request request1 = new Request.Builder().url("ws://localhost:" + port + "/ws?id=test1").build();

        WebSocket webSocket = client.newWebSocket(request1, new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                System.out.println("websocket onOpen");
            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        webSocket.close(1000,"success");
        client.dispatcher().executorService().shutdown();
    }

    @Test
    void testChat(){
        OkHttpClient client = new OkHttpClient();
        Request request1 = new Request.Builder().url("ws://localhost:" + port + "/ws?from=test1&to=test2").build();
        Request request2 = new Request.Builder().url("ws://localhost:" + port + "/ws?from=test2&to=test1").build();

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("WebSocket opened: " + response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println("Received message: " + text);
            }

            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                System.out.println("WebSocket closed: " + reason);
            }
        };
        WebSocket socket1 = client.newWebSocket(request1,listener);
        WebSocket socket2 = client.newWebSocket(request2,listener);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "text");
        jsonObject.addProperty("content", "hello");

        socket1.send(jsonObject.toString());
        socket2.send(jsonObject.toString());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        socket1.close(1000,"success");
        socket2.close(1000,"success");
        client.dispatcher().executorService().shutdown();
    }



}
