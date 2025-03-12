package komeiji.modules.websocket.session;

import io.netty.channel.Channel;

import java.text.ParseException;

public interface Session {
    Channel getConnect();
    SessionToken getId();
    SessionToken getTarget();

    void parseUrl(String url) throws ParseException;
}
