package komeiji.back.service.Impl;

import komeiji.back.service.UserLoginService;
import komeiji.back.repository.UserDao;
import komeiji.back.entity.User;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService{
    @Resource
    private UserDao userDao;


    @Override
    public User loginService(String uname, String password) {
        User user = userDao.findByUnameAndPassword(uname, password);
        if(user != null){ user.setPassword("");}
        return user;
    }

    @Override
    public User registerService(User user) {
        if(userDao.findByUname(user.getUname()) != null){
            return null; //用户名重复
        }
        else {
            User newuser = userDao.save(user);
            if(newuser != null){ newuser.setPassword(""); }
            return newuser;
        }
    }
}
