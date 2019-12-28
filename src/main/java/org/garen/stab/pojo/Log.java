package org.garen.stab.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    private String logType;
    private String data;
    private String createTime;
}
