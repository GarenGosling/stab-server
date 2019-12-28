package org.garen.stab.service.impl;

import java.io.IOException;
import java.util.List;

import org.garen.stab.dao.UserDao;
import org.garen.stab.model.UserVo;
import org.garen.stab.pojo.User;
import org.garen.stab.response.BusinessException;
import org.garen.stab.service.UserService;
import org.garen.stab.util.MD5Utils;
import org.garen.stab.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class UserServiceImpl implements UserService {

    @Value("${stab.salt}")
    private String STAB_SALT;

    @Autowired
    UserDao userDao;


    public User signUp(UserVo vo) {
        validUser(vo);
        User user = new User();
        user.setPhone(vo.getPhone());
        user.setPassword(MD5Utils.MD5(vo.getPassword()));
        user.setRealName(vo.getRealName());
        user.setStatus(0);
        if("root".equals(vo.getPhone())){
            user.setStatus(1);
        }
        userDao.save(user);
        return user;
    }

    public String signIn(UserVo vo) {
        validUser(vo);
        User user = userDao.get(vo.getPhone());
        if (user == null) {
            throw new BusinessException("login fail");
        }
        if (!MD5Utils.MD5(vo.getPassword()).equals(user.getPassword())) {
            throw new BusinessException("password mistake");
        }
        if(user.getStatus() != 1){
            throw new BusinessException("User unavailable, wait audit");
        }
        String tokenBase = vo.getPhone() + "-" + STAB_SALT;
        return new BASE64Encoder().encode(tokenBase.getBytes());
    }

    public User signInToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException("token is null");
        }
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(token);
            String tokenBase = new String(bytes);
            String phone = tokenBase.split("-")[0];
            return userDao.get(phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User audit(String token, String phone) {
        User root = signInToken(token);
        if(!"root".equals(root.getPhone())){
            throw new BusinessException("Root is required");
        }
        User user = userDao.get(phone);
        user.setStatus(1);
        userDao.update(user);
        return user;
    }

    @Override
    public void delUser(String password, String phone) {
        validRoot(password);
        userDao.delete(phone);
    }

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public User get(String phone) {
        return userDao.get(phone);
    }

    private void validUser(UserVo vo) {
        if (vo == null) {
            throw new BusinessException("param is null");
        }
        if (StringUtils.isEmpty(vo.getPhone())) {
            throw new BusinessException("phone is null");
        }
        if (!PhoneUtil.isPhone(vo.getPhone()) && !vo.getPhone().equals("root")) {
            throw new BusinessException("Not the phone number");
        }
        if (StringUtils.isEmpty(vo.getPassword())) {
            throw new BusinessException("password is null");
        }
    }

    private void validRoot(String password) {
        if(StringUtils.isEmpty(password)){
            throw new BusinessException("password is null");
        }
        User root = userDao.get("root");
        if(!MD5Utils.MD5(password).equals(root.getPassword())){
            throw new BusinessException("Root is required");
        }
    }
}
