package org.garen.stab.util.file;

import org.garen.stab.response.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;


@Component
public class FileUtil {

    public static File assertFile(String filePath, String split) throws IOException {
        String folderPath = PathUtil.getFolderPath(filePath, split);
        FolderUtil.assertFolder(folderPath, split);
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public static void deleteFile(String filePath) {
        if(!StringUtils.isEmpty(filePath)){
            File file = new File(filePath);
            if(file.exists()){
                file.delete();
            }
        }
    }

    public static void validPathThrow(String path) {
        File file = new File(path);
        if(!file.exists()){
            throw new BusinessException("path does not exist");
        }
    }

    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

//    public static void main(String[] args) throws IOException {
//        String filePath = "/Users/liuxueliang/Desktop/hello";
////        assertFile(filePath, "/");
//        deleteFile(filePath);
//    }

}
