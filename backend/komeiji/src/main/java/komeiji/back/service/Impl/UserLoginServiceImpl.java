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
    public Boolean loginService(String userName, String password) {
        User user = userDao.findByUserNameAndPassword(userName, password);
        return user != null;
    }

    @Override
    public Boolean registerService(User user) {
        if(userDao.findByUserName(user.getUserName()) != null){
            //用户名重复
            return false;
        }
        else {
            userDao.save(user);
            return true;
        }
    }

    @Override
    public User getUserByName(String userName) {
        return userDao.findByUserName(userName);
    }
}
