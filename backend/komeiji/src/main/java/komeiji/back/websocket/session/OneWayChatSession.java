package komeiji.back.websocket.session;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Setter
public final class OneWayChatSession implements Session {
    private static Logger logger = LoggerFactory.getLogger(OneWayChatSession.class);

    private SessionToken userId;
    private Channel channel;
    @Getter
    private SessionToken targetId;

    public void parseUrl(String url) throws ParseException {
        if(url ==null || url.isEmpty()) {
            throw new ParseException("url is null or empty", 0);
        }
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        Map<String, List<String>> parameters = decoder.parameters();

        if (!parameters.containsKey("from") || !parameters.containsKey("to")){
            throw new ParseException("invalid parameters", 0);
        }

        if (parameters.get("from").isEmpty() || parameters.get("to").isEmpty()) {
            throw new ParseException("empty parameters", 0);
        }

        userId = new SessionToken(parameters.get("from").get(0));
        targetId = new SessionToken(parameters.get("to").get(0));
    }


    public OneWayChatSession(String url, Channel channel) {
        try {
            parseUrl(url);
            this.channel = channel;
        }
        catch (ParseException e){
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Channel getConnect() {
        return channel;
    }

    @Override
    public SessionToken getId() {
        return userId;
    }

    @Override
    public SessionToken getTarget() {
        return targetId;
    }

    @Override
    public String toString() {
       return String.format("OneWayChatSession{userId=%s, targetId=%s}", userId, targetId);
    }
}
