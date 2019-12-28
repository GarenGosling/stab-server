package org.garen.stab.service.impl;

import org.garen.stab.dao.LogDao;
import org.garen.stab.dao.UserDao;
import org.garen.stab.pojo.Log;
import org.garen.stab.pojo.User;
import org.garen.stab.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;
    @Autowired
    UserDao userDao;

    @Override
    public void save(String phone, Log log) {
        logDao.save(phone, log);
    }

    @Override
    public Map<String, Object> userLogs(String phone) {
        List<Log> logs = logDao.userLogs(phone);
        User user = userDao.get(phone);
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("result", logs);
        return map;
    }
}
