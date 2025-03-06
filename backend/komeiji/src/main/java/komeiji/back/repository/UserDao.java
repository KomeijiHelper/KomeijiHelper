package komeiji.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import komeiji.back.entity.User;
import org.springframework.stereotype.*;

@Repository
public interface UserDao extends JpaRepository<User,Long>{

    User findById(long id);
    User findByUserName(String uname);
    User findByIdAndPassword(long id, String password);
    User findByUserNameAndPassword(String uname, String password);

}
