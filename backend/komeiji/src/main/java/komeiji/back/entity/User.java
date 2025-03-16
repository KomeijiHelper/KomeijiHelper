package komeiji.back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Table(name = "user")
@Entity
@Schema(name = "User", description = "用户实体类")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "用户ID",example = "1")
    private long id;

    @Schema(description = "用户名",example = "admin",required = true)
    private String userName;

    @Schema(description = "密码",example = "123456",required = true)
    private String password;

    @Schema(description = "用户类别",example = "0")
    private UserClass userClass = UserClass.Normal;

    @Schema(description = "邮箱",example = "admin@example.com")
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String uname) {
        this.userName = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserClass getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClass userClass) {
        this.userClass = userClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
