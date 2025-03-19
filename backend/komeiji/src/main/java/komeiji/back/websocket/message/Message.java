package komeiji.back.websocket.message;

import komeiji.back.websocket.session.SessionToken;
import lombok.Data;


@Data
public abstract class Message {
    private SessionToken from;
    private SessionToken to;


    public Message(SessionToken from, SessionToken to) {
        this.from = from;
        this.to = to;
    }

    // We need to decode by ourselves the content send to Websocket channel
    public abstract Object messageDecode();
}
