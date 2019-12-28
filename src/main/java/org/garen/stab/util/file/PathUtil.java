package org.garen.stab.util.file;

import org.garen.stab.response.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PathUtil {

    public static String getFolderPath(String filePath, String split) {
        if(StringUtils.isEmpty(filePath)){
            throw new BusinessException("filePath is null");
        }
        if(StringUtils.isEmpty(split)){
            throw new BusinessException("split is null");
        }
        return filePath.substring(0, filePath.lastIndexOf(split));
    }

//    public static void main(String[] args) {
//        String filePath = "/Users/liuxueliang/Desktop/hello";
//        System.out.println(getFolderPath(filePath, "/"));
//    }
}
