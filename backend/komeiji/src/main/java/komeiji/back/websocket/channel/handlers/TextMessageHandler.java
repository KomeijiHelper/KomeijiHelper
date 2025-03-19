package komeiji.back.websocket.channel.handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.channel.Attributes;
import komeiji.back.websocket.channel.events.MessageForwardEvent;
import komeiji.back.websocket.message.MessageType;
import komeiji.back.websocket.message.impl.TextMessage;
import komeiji.back.websocket.session.Session;
import komeiji.back.websocket.session.SessionToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        System.out.println("服务器收到消息 " + text);

        JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        String content = jsonObject.get("content").getAsString();

        Session session = ctx.channel().attr(Attributes.SESSION).get();
        MessageType messageType = MessageType.fromString(type);

        switch (messageType) {
            case CONSULTANT_REQUEST -> {
                // 转发请求给咨询师
                SessionToken consultantToken = new SessionToken(content);
                Session consultantSession = WebSocketServer.getWebSocketSingleServer()
                    .getSessionManager()
                    .findSession(consultantToken);
                
                if (consultantSession != null) {
                    TextMessage requestMessage = new TextMessage(
                        session.getId(),
                        consultantToken,
                        "新的咨询请求"
                    );
                    ctx.fireUserEventTriggered(new MessageForwardEvent(requestMessage));
                }
            }
            case CONSULTANT_ACCEPTED, CONSULTANT_REJECTED -> {
                // 转发响应给用户
                Session userSession = WebSocketServer.getWebSocketSingleServer()
                    .getSessionManager()
                    .findSession(session.getTarget());
                
                if (userSession != null) {
                    TextMessage responseMessage = new TextMessage(
                        session.getId(),
                        userSession.getId(),
                        messageType == MessageType.CONSULTANT_ACCEPTED ? "咨询师已接受请求" : "咨询师已拒绝请求"
                    );
                    ctx.fireUserEventTriggered(new MessageForwardEvent(responseMessage));
                }
            }
            default -> {
                // 处理普通文本消息
                Session targetSession = WebSocketServer.getWebSocketSingleServer()
                    .getSessionManager()
                    .findSession(session.getTarget());
                
                if (targetSession != null) {
                    TextMessage textMessage = new TextMessage(
                        session.getId(),
                        targetSession.getId(),
                        content
                    );
                    ctx.fireUserEventTriggered(new MessageForwardEvent(textMessage));
                }
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close();
    }
}