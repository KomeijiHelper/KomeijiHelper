package komeiji.back.service;

import komeiji.back.websocket.WebSocketServer;
import komeiji.back.websocket.message.MessageFactory;
import komeiji.back.websocket.message.MessageType;
import komeiji.back.websocket.session.Session;
import komeiji.back.websocket.session.SessionToken;
import org.springframework.stereotype.Service;

@Service
public class ConsultantService {

    public void handleConsultantRequest(String consultantId, String userId) {
        // 这里可以添加额外的业务逻辑，比如：
        // 1. 检查咨询师是否在线
        // 2. 检查咨询师是否已被其他用户选择
        // 3. 记录咨询请求到数据库
        
        // 发送WebSocket消息给咨询师
        Session consultantSession = WebSocketServer.getWebSocketSingleServer()
            .getSessionManager()
            .findChatSession(new SessionToken(consultantId));
            
        if (consultantSession != null) {
            consultantSession.getConnect().writeAndFlush(
                MessageFactory.newTextMessage(
                    MessageType.CONSULTANT_REQUEST,
                    new SessionToken(userId),
                    new SessionToken(consultantId),
                    userId
                ).messageDecode()
            );
        }
    }
} 