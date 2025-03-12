package komeiji.modules.websocket.protocol.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import komeiji.modules.websocket.message.MessageFactory;
import komeiji.modules.websocket.message.MessageType;
import komeiji.modules.websocket.message.impl.TextMessage;
import komeiji.modules.websocket.protocol.TextFrameProtocol;
import komeiji.modules.websocket.session.Session;

class MessageBody {

    public MessageType type;
    public String content;

    public MessageBody(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public static MessageBody buildMessageBody(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        MessageType type = MessageType.fromString(jsonObject.get("type").getAsString());
        String content = jsonObject.get("content").getAsString();
        return new MessageBody(type, content);
    }
}

public class DefaultTextFrameProtocol extends TextFrameProtocol<TextMessage> {

    @Override
    public TextMessage frameTransform(TextWebSocketFrame frame, Session session) {
        String text = frame.text();
        System.out.println(text);

        MessageBody body = MessageBody.buildMessageBody(text);

        return MessageFactory.newTextMessage(body.type,session.getId(),session.getTarget(),body.content);
    }


}
