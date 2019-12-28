package org.garen.stab.service;

import java.util.Map;
import org.garen.stab.model.SqlVo;

public interface SqlService {
    Map<String, Object> execute(String token, SqlVo paramSqlVo);
}
