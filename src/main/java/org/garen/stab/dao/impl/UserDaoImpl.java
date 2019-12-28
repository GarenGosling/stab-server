package org.garen.stab.dao.impl;

import org.garen.stab.component.SerializeComponent;
import org.garen.stab.dao.UserDao;
import org.garen.stab.pojo.User;
import org.garen.stab.response.BusinessException;
import org.garen.stab.util.PhoneUtil;
import org.garen.stab.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {

    @Value("${stab.dataPath}")
    private String STAB_DATA_PATH;
    @Value("${stab.split}")
    private String STAB_SPLIT;

    @Autowired
    SerializeComponent serializeComponent;

    @Override
    public void save(User user) {
        if(user == null){
            throw new BusinessException("param is null");
        }
        if(StringUtils.isEmpty(user.getPhone())){
            throw new BusinessException("phone is null");
        }
        String filePath = getFolder() + user.getPhone();
        User u = serializeComponent.getT(filePath);
        if(u != null){
            throw new BusinessException("user is exist");
        }
        serializeComponent.saveT(filePath, STAB_SPLIT, user);
    }

    @Override
    public void update(User user) {
        if(user == null){
            throw new BusinessException("param is null");
        }
        if(StringUtils.isEmpty(user.getPhone())){
            throw new BusinessException("phone is null");
        }
        String filePath = getFolder() + user.getPhone();
        User u = serializeComponent.getT(filePath);
        if(u == null){
            throw new BusinessException("user is not exist");
        }
        serializeComponent.saveT(filePath, STAB_SPLIT, user);
    }

    @Override
    public void delete(String phone) {
        if(StringUtils.isEmpty(phone)){
            throw new BusinessException("phone is null");
        }
        String filePath = getFolder() + phone;
        FileUtil.deleteFile(filePath);
    }

    @Override
    public User get(String phone) {
        if(StringUtils.isEmpty(phone)){
            throw new BusinessException("phone is null");
        }
        String filePath = getFolder() + phone;
        return (User) serializeComponent.getT(filePath);
    }

    @Override
    public List<User> listAll() {
        List<String> fileNames = serializeComponent.getFileNames(getFolder(), STAB_SPLIT);
        List<User> list = new ArrayList<>();
        for(String fileName : fileNames){
            if(!"root".equals(fileName) && !PhoneUtil.isPhone(fileName)){
                continue;
            }
            User user = get(fileName);
            list.add(user);
        }
        return list;
    }

    private String getFolder() {
        return STAB_DATA_PATH + "user" + STAB_SPLIT;
    }

}
