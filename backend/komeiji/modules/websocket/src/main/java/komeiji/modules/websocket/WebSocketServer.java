package komeiji.modules.websocket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import komeiji.modules.websocket.channel.handlers.MessageForwardHandler;
import komeiji.modules.websocket.channel.handlers.TextMessageHandler;
import komeiji.modules.websocket.channel.handlers.TextWebSocketHandler;
import komeiji.modules.websocket.channel.handlers.WebSocketConnectHandler;
import komeiji.modules.websocket.message.fowardqueue.MessageForwardQueue;
import komeiji.modules.websocket.session.SessionManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebSocketServer {

    private static WebSocketServer server = null;

    public static WebSocketServer getWebSocketSingleServer(LogLevel logLevel, int httpMaxContentLength, String url,
                                                           SessionManager sessionManager, MessageForwardQueue messageForwardQueue) {
        if (server != null) {
            logger.error("Websocket server has already been build");
            System.exit(1);
        }
        server = new WebSocketServer(logLevel, httpMaxContentLength, url, sessionManager,messageForwardQueue);
        return server;
    }

    public static WebSocketServer getWebSocketSingleServer() {
        if(server == null) {
            logger.error("Websocket server has not been build");
            System.exit(1);
        }
        return server;
    }

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final EventLoopGroup msgForwardGroup;

    @Getter
    private final MessageForwardQueue messageForwardQueue;

    @Getter
    private final SessionManager sessionManager;
    private ServerBootstrap bootstrap;

    public WebSocketServer(LogLevel logLevel,int httpMaxContentLength,String url,
                           SessionManager sessionManager,MessageForwardQueue messageForwardQueue) {
       this.bossGroup = new NioEventLoopGroup();
       this.workerGroup = new NioEventLoopGroup();
       this.msgForwardGroup = new DefaultEventLoopGroup();
       this.sessionManager = sessionManager;
       this.messageForwardQueue = messageForwardQueue;
       InitServer(logLevel,httpMaxContentLength,url);
    }

    private void InitServer(LogLevel logLevel,int httpMaxContentLength,String url) {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(logLevel))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpObjectAggregator(httpMaxContentLength));

                            pipeline.addLast(new WebSocketServerProtocolHandler(url,true));
                            pipeline.addLast(new WebSocketConnectHandler());
                            pipeline.addLast(new TextWebSocketHandler());
                            pipeline.addLast(new TextMessageHandler());
                            pipeline.addLast(msgForwardGroup,new MessageForwardHandler());
                        }
                    });
    }

    public void startServer(int port) {
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            msgForwardGroup.shutdownGracefully();
        }
    }

}