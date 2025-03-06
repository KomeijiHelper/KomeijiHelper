package komeiji.back.service;

import komeiji.back.entity.User;
import komeiji.back.entity.UserClass;

import java.util.List;

public interface UserService {
    Boolean loginService(String userName ,String password);
    Boolean registerService(User user);
    User getUserByName(String uname);
    List<User> getUsersByUserClass(UserClass userClass);
}
