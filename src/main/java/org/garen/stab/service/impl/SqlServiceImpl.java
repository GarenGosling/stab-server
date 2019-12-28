package org.garen.stab.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.garen.stab.enums.LogType;
import org.garen.stab.model.SqlVo;
import org.garen.stab.pojo.Log;
import org.garen.stab.pojo.User;
import org.garen.stab.response.BusinessException;
import org.garen.stab.service.LogService;
import org.garen.stab.service.SqlService;
import org.garen.stab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class SqlServiceImpl implements SqlService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @Autowired
    LogService logService;

    public Map<String, Object> execute(String token, SqlVo vo) {
        User user = this.userService.signInToken(token);
        if (user == null) {
            throw new BusinessException("Authentication failed");
        }
        String sql = validSql(vo);
        logService.save(user.getPhone(), new Log(LogType.SQL.toString(), sql, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS"))));
        if (sql.startsWith("insert")) {
            return insert(sql);
        }
        if (sql.startsWith("update")) {
            return update(sql);
        }
        if (sql.startsWith("select")) {
            return select(sql);
        }
        throw new BusinessException("No permissions or syntax errors");
    }

    private String validSql(SqlVo vo) {
        if (vo == null) {
            throw new BusinessException("Parameters cannot be empty");
        }
        if (StringUtils.isEmpty(vo.getSql())) {
            throw new BusinessException("sql cannot be empty");
        }
        String sql = vo.getSql();
        if (!";".equals(sql.substring(sql.length() - 1))) {
            throw new BusinessException("No end sign");
        }
        return sql;
    }

    private Map<String, Object> insert(String sql) {
        Map<String, Object> map = new HashMap<>();
        int insert = this.jdbcTemplate.update(sql);
        map.put("sql", sql);
        map.put("result", insert);
        map.put("method", "INSERT");
        return map;
    }

    private Map<String, Object> update(String sql) {
        if (!sql.contains("where")) {
            throw new BusinessException("The update sql must contain the [where] keyword");
        }
        Map<String, Object> map = new HashMap<>();
        int update = this.jdbcTemplate.update(sql);
        map.put("sql", sql);
        map.put("result", update);
        map.put("method", "UPDATE");
        return map;
    }

    private Map<String, Object> select(String sql) {
        Map<String, Object> map = new HashMap<>();
        if (!sql.contains("limit")) {
            sql = sql.substring(0, sql.length() - 1) + " limit 24;";
        }
        map.put("sql", sql);
        map.put("result", this.jdbcTemplate.queryForList(sql));
        map.put("method", "QUERY");
        return map;
    }
}
