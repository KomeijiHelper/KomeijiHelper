package komeiji.modules.websocket.message.impl;

import com.google.gson.Gson;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import komeiji.modules.websocket.message.Message;
import komeiji.modules.websocket.session.SessionToken;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class TextMessage extends Message {
    private static Gson gson = new Gson();
    private String data;

    public TextMessage(SessionToken from, SessionToken to) {
        super(from,to);
    }


    @Override
    public TextWebSocketFrame messageDecode() {
        return new TextWebSocketFrame(gson.toJson(Map.of("type","text","content",data)));
    }

    public TextMessage(SessionToken from, SessionToken to, String data) {
        this(from,to);
        this.data = data;
    }


    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
