package org.garen.stab.service;

import org.garen.stab.pojo.Log;

import java.util.Map;

public interface LogService {
    void save(String phone, Log log);
    Map<String, Object> userLogs(String phone);
}
