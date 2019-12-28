package org.garen.stab.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String phone;
    private String password;
    private String realName;
    private Integer status; // 0 不可用  1 可用
}
