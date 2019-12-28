package org.garen.stab.service;

import org.garen.stab.model.UserVo;
import org.garen.stab.pojo.User;

import java.util.List;

public interface UserService {

    User signUp(UserVo paramUserVo);

    String signIn(UserVo paramUserVo);

    User signInToken(String token);

    User audit(String token, String phone);

    void delUser(String password, String phone);

    List<User> listAll();

    User get(String phone);
}
