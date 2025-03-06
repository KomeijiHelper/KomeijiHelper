package komeiji.back.service;

import komeiji.back.entity.User;
public interface UserLoginService {
    Boolean loginService(String userName ,String password);
    Boolean registerService(User user);
    User getUserByName(String uname);
}
