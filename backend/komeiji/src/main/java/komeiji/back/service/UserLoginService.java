package komeiji.back.service;

import komeiji.back.entity.User;
public interface UserLoginService {

    User loginService(String uname ,String password);
    User registerService(User user);
    User getUserByName(String uname);
}
