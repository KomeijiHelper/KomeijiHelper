package komeiji.modules.websocket.session;



public record SessionToken(String token) {

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        SessionToken sessionToken = (SessionToken) obj;
        return token.equals(sessionToken.token);
    }
}
