package komeiji.back.websocket.session;

import io.netty.channel.group.ChannelGroup;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private final ChannelGroup channels;
    @Getter
    private final Map<SessionToken, Session> chatSessions = new HashMap<>();
    private final Map<SessionToken, Session> userSessions = new HashMap<>();


    public SessionManager(ChannelGroup channels) {
        this.channels = channels;
    }

    /**
     * Session的添加在WebSocketConnectHandler里面，此处不知道具体Session是哪一类，需要在该方法中确定并存储到对应的Map中
     * removeSession方法同理
     */
    public void addSession(Session session){
        channels.add(session.getConnect());
        if (session instanceof OneWaySession) {
            chatSessions.put(session.getId(),session);
        }
        else if (session instanceof UserSession) {
            userSessions.put(session.getId(),session);
        }
        else {
            logger.warn("Add unknown session class: {}", session.getClass().getName());
        }
    }

    public void removeSession(Session session){
        if (session instanceof OneWaySession) {
            chatSessions.remove(session.getId());
        }
        else if (session instanceof UserSession) {
            userSessions.remove(session.getId());
        }
        else {
            logger.warn("Remove unknown session class: {}", session.getClass().getName());
        }
        // auto remove channel when disconnect channel
    }

    public void removeChatSession(Session session){
        assert session instanceof OneWaySession;
        chatSessions.remove(session.getId());
        // auto remove channel when disconnect channel
    }

    public Session findChatSession(SessionToken sessionId){
        return chatSessions.get(sessionId);
    }


    public void removeUserSession(Session session){
        assert session instanceof UserSession;
        userSessions.remove(session.getId());
        // auto remove channel when disconnect channel
    }

    public Session findUserSession(SessionToken sessionId){
        return userSessions.get(sessionId);
    }

}
