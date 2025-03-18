package komeiji.modules.websocket.message;

public enum MessageType {
    TEXT_MESSAGE,
    BINARY_MESSAGE;

    public static MessageType fromString(String text) {
        return switch (text.toLowerCase()) {
            case "text" -> TEXT_MESSAGE;
            case "binary" -> BINARY_MESSAGE;
            default -> throw new IllegalArgumentException("Invalid MessageType: " + text);
        };
    }

}
