package komeiji.back.websocket.session;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class UserSession implements Session{
    private static final Logger logger = LoggerFactory.getLogger(UserSession.class);

    private SessionToken userId;
    private final Channel channel;
    @Getter
    private SessionToken targetId;

    public void parseUrl(String url) throws ParseException {
        if(url ==null || url.isEmpty()) {
            throw new ParseException("url is null or empty", 0);
        }
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        Map<String, List<String>> parameters = decoder.parameters();

        if (!parameters.containsKey("id") ){
            throw new ParseException("invalid parameters", 0);
        }

        if (parameters.get("id").isEmpty()) {
            throw new ParseException("empty parameters", 0);
        }

        userId = new SessionToken(parameters.get("id").get(0));
        targetId = new SessionToken(parameters.get("id").get(0));
    }


    public UserSession(String url,Channel channel) {
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
        return String.format("UserSession{id=%s}", userId);
    }
}
