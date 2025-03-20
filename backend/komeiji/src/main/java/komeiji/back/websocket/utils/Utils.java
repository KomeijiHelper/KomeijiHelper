package komeiji.back.websocket.utils;


import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.message.Message;
import komeiji.back.websocket.session.Session;
import komeiji.back.websocket.session.SessionManager;
import komeiji.back.websocket.session.SessionToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO:
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String imageBytes2Base64(byte[] bytes) {
        return null;
    }

    public static byte[] imageBase642Bytes(String base64) {
        return null;
    }

    /**
     * Used for send message in UserSessions
     * @param message message to be sent
     */
    public static void sendMessageInUserSession(Message message) {
        WebSocketServer.getWebSocketSingleServer().getMessageForwardQueue().registerMessage(message);
    }

    /**
     * 消息的发送分为三种：
     * 一种是通过OneWayChatSession从自己向对方发送消息
     * 一种是通过UserSession由服务器主动向目标推送消息
     * ChatRequest则是第三种（可能未来有其他同类型的），需要在UserSession中主动转发到别的UserSession
     * @param useMessageQueue 是否使用消息队列转发，如果是则返回正常值；否则主动发送，返回发送结果
     */
    public static int forwardMessageInUserSession(Message message,boolean useMessageQueue){
        if(useMessageQueue) {
            WebSocketServer.getWebSocketSingleServer().getMessageForwardQueue().registerMessage(message);
            return 0;
        }
        SessionToken from = message.getFrom();
        SessionToken to = message.getTo();
        SessionManager sm = WebSocketServer.getWebSocketSingleServer().getSessionManager();
        Session toSession;
        if(sm.findUserSession(from) == null) {
            logger.warn("message's from is unavailable:{}", from);
            return -1;
        }

        if((toSession=sm.findUserSession(to)) == null) {
            logger.warn("message's to is unavailable:{}", to);
            return -2;
        }

        toSession.getConnect().writeAndFlush(message.messageDecode());
        return 0;
    }

    public static int forwardMessageInUserSession(Message message){
        return forwardMessageInUserSession(message,false);
    }
}
