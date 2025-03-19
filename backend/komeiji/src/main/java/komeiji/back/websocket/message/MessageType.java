package komeiji.back.websocket.message;

public enum MessageType {
    TEXT_MESSAGE,
    BINARY_MESSAGE,
    CONSULTANT_REQUEST,
    CONSULTANT_ACCEPTED,
    CONSULTANT_REJECTED;

    public static MessageType fromString(String text) {
        return switch (text.toLowerCase()) {
            case "text" -> TEXT_MESSAGE;
            case "binary" -> BINARY_MESSAGE;
            case "consultant_request" -> CONSULTANT_REQUEST;
            case "consultant_accepted" -> CONSULTANT_ACCEPTED;
            case "consultant_rejected" -> CONSULTANT_REJECTED;
            default -> throw new IllegalArgumentException("Invalid MessageType: " + text);
        };
    }

}
