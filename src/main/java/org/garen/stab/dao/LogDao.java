package org.garen.stab.dao;

import org.garen.stab.pojo.Log;

import java.util.List;

public interface LogDao {
    void save(String phone, Log log);
    List<Log> userLogs(String phone);
}
