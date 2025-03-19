package komeiji.back.websocket.session;

import io.netty.channel.Channel;
import komeiji.back.websocket.WebSocketServer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionBuilder {

    private static final String baseUri = WebSocketServer.getWebSocketSingleServer().getUrl();
    private static final Pattern chatPattern = Pattern.compile(String.format("^%s\\?from=[^&]+&to=[^&]+$", baseUri));
    private static final Pattern userPattern = Pattern.compile(String.format("^%s\\?id=[^&]+$", baseUri));
    public static Session createSession(String uri, Channel channel) {
        Matcher chatMatcher = chatPattern.matcher(uri);
        Matcher userMatcher = userPattern.matcher(uri);
        if(chatMatcher.matches()) {
            return new OneWaySession(uri,channel);
        }
        else if (userMatcher.matches()) {
            return new UserSession(uri,channel);
        }
        else {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }
}
