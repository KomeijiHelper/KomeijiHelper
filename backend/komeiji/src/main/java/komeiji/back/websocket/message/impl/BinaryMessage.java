package komeiji.back.websocket.message.impl;

import io.netty.buffer.ByteBuf;
import komeiji.back.websocket.message.Message;
import komeiji.back.websocket.session.SessionToken;
import lombok.Getter;
import lombok.Setter;

// TODO:
@Getter
@Setter
public class BinaryMessage extends Message {
    private ByteBuf data;

    public BinaryMessage(SessionToken from, SessionToken to) {
        super(from, to);
    }

    public BinaryMessage(SessionToken from, SessionToken to, ByteBuf data) {
        this(from, to);
        this.data = data;
    }

    @Override
    public Object messageDecode() {
        return null;
    }
}
