package org.garen.stab.dao;

import org.garen.stab.pojo.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    void update(User user);
    void delete(String phone);
    User get(String phone);
    List<User> listAll();
}
