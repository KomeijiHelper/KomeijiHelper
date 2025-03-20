package komeiji.back.websocket.message.impl;

import komeiji.back.websocket.session.SessionToken;

public class ChatConnectMessage extends TextMessage{

    // TODO:
    public ChatConnectMessage(SessionToken from, SessionToken to) {
        super(from, to);
    }

    public ChatConnectMessage(SessionToken from, SessionToken to, String data) {
        super(from, to, data);
    }
}
