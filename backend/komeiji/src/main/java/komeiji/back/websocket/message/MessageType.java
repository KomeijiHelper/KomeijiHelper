package komeiji.back.websocket.message;

public enum MessageType {
    // two basic type
    TEXT_MESSAGE,
    BINARY_MESSAGE,

    // subtype of text
    NOTIFY_MESSAGE,
    CHAT_REQUEST,
    CHAT_CONNECT,

    // subtype of binary
    IMAGE_MESSAGE;


    public static MessageType fromString(String text) {
        return switch (text.toLowerCase()) {
            case "text" -> TEXT_MESSAGE;
            case "binary" -> BINARY_MESSAGE;
            case "chat_request" -> CHAT_REQUEST;
            case "chat_connect" -> CHAT_CONNECT;
            default -> throw new IllegalArgumentException("Invalid MessageType: " + text);
        };
    }

}
