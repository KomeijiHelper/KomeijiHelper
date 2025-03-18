package komeiji.modules.websocket.session;

import io.netty.channel.Channel;

import java.text.ParseException;

// TODO: future work
public class GroupSession implements Session {

    @Override
    public Channel getConnect() {
        return null;
    }

    @Override
    public SessionToken getId() {
        return null;
    }

    @Override
    public void parseUrl(String url) throws ParseException {

    }

    @Override
    public SessionToken getTarget() {
        return null;
    }
}

