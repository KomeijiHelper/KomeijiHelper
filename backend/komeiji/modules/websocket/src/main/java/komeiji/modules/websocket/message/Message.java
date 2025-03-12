package komeiji.modules.websocket.message;

import komeiji.modules.websocket.session.SessionToken;
import lombok.Data;


@Data
public abstract class Message implements MessageHandler {
    private SessionToken from;
    private SessionToken to;


    public Message(SessionToken from, SessionToken to) {
        this.from = from;
        this.to = to;
    }

}
