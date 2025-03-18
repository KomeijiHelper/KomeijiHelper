package komeiji.modules.websocket.session;

import io.netty.channel.group.ChannelGroup;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private final ChannelGroup channels;
    @Getter
    private final Map<SessionToken,Session> sessions = new HashMap<>();

    public SessionManager(ChannelGroup channels) {
        this.channels = channels;
    }
    public void addSession(Session session){
        sessions.put(session.getId(),session);
        channels.add(session.getConnect());
    }

    public void removeSession(Session session){
        sessions.remove(session.getId());
        // auto remove channel when disconnect channel
    }

    public Session findSession(SessionToken sessionId){
        if(!sessions.containsKey(sessionId)){
            return null;
        }
        return sessions.get(sessionId);
    }

}
