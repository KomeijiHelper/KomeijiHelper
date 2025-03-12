package komeiji.modules.websocket.message.impl;

import com.google.gson.Gson;
import komeiji.modules.websocket.message.Message;
import komeiji.modules.websocket.session.SessionToken;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TextMessage extends Message {
    private String data;

    public TextMessage(SessionToken from, SessionToken to) {
        super(from,to);
    }

    public TextMessage(SessionToken from, SessionToken to,String data) {
        this(from,to);
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
