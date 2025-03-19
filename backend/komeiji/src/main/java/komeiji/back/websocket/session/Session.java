package komeiji.back.websocket.session;

import io.netty.channel.Channel;

import java.text.ParseException;

// 此处的Session是Netty中连接channel的封装
public interface Session {
    Channel getConnect();
    SessionToken getId();
    SessionToken getTarget();

    void parseUrl(String url) throws ParseException;
}
