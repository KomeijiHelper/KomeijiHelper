package komeiji.back.websocket.persistence;

import komeiji.back.websocket.session.Session;

import java.util.Date;
import java.util.List;

public class Conversation {
    // TODO: 根据from和to的token来设置一个唯一的CID,from和to的顺序不影响
    private String CID;
    private Date StartTime;
    private List<MessageRecord> records;

    private Conversation(){}
    private Conversation(String cid, Date startTime, List<MessageRecord> records){
        this.CID = cid;
        this.StartTime = startTime;
        this.records = records;
    }

    public static Conversation newConversationInstance(Session session1, Session session2){
        return null;
    }

    public void addRecord(MessageRecord record) {
        records.add(record);
    }
}
