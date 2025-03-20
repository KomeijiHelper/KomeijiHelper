package komeiji.back.websocket.message.impl;

import com.google.gson.Gson;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import komeiji.back.websocket.message.Message;
import komeiji.back.websocket.session.SessionToken;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ChatRequestMessage extends Message {
    private static Gson gson = new Gson();
    private String consultantId;

    public ChatRequestMessage(SessionToken from, SessionToken to, String consultantId) {
        super(from, to);
        this.consultantId = consultantId;
    }

    @Override
    public TextWebSocketFrame messageDecode() {
        return new TextWebSocketFrame(gson.toJson(Map.of(
            "type", "consultant_request",
            "content", consultantId
        )));
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
} 