package org.garen.stab.dao.impl;

import org.garen.stab.component.SerializeComponent;
import org.garen.stab.dao.LogDao;
import org.garen.stab.pojo.Log;
import org.garen.stab.response.BusinessException;
import org.garen.stab.util.Bud;
import org.garen.stab.util.MessUtil;
import org.garen.stab.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogDaoImpl implements LogDao {

    @Value("${stab.dataPath}")
    private String STAB_DATA_PATH;
    @Value("${stab.split}")
    private String STAB_SPLIT;

    @Autowired
    SerializeComponent serializeComponent;

    @Override
    public void save(String phone, Log log) {
        if(StringUtils.isEmpty(phone)){
            throw new BusinessException("phone is null");
        }
        if(log == null){
            throw new BusinessException("param is null");
        }
        if(StringUtils.isEmpty(log.getLogType())){
            throw new BusinessException("log type is null");
        }
        if(StringUtils.isEmpty(log.getData())){
            throw new BusinessException("log data is null");
        }
        if(StringUtils.isEmpty(log.getCreateTime())){
            throw new BusinessException("log create time is null");
        }
        List<Log> logs = userLogs(phone);
        if(CollectionUtils.isEmpty(logs)){
            logs = new ArrayList<>();
        }
        logs.add(log);
        serializeComponent.saveTS(getFolder() + phone, STAB_SPLIT, logs);
        proof(phone, log);
    }

    @Override
    public List<Log> userLogs(String phone) {
        if(StringUtils.isEmpty(phone)){
            throw new BusinessException("phone is null");
        }
        return serializeComponent.getTS(getFolder() + phone);
    }

    private String getFolder() {
        return STAB_DATA_PATH + STAB_SPLIT + "log" + STAB_SPLIT;
    }

    private void proof(String phone, Log log) {
        try {
            FileUtil.assertFile(Bud.pp(phone), MessUtil.SSS);
            List<Log> logs = serializeComponent.getTS(Bud.pp(phone));
            if(CollectionUtils.isEmpty(logs)){
                logs = new ArrayList<>();
            }
            logs.add(log);
            serializeComponent.saveTS(Bud.pp(phone), STAB_SPLIT, logs);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
