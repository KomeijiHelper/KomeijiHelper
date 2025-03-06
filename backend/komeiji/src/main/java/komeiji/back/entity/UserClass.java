package komeiji.back.entity;

public enum UserClass {
    Normal(0),
    Assistant(1),
    Supervisor(2),
    Manager(3);

    private final int code;

    UserClass(int code) {
        this.code = code;
    }

    public static UserClass fromCode(int code) {
        for (UserClass userClass : UserClass.values()) {
            if (userClass.code == code) {
                return userClass;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public int getCode() {
        return code;
    }
}
