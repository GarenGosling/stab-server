package org.garen.stab.component;

import org.garen.stab.pojo.Log;
import org.garen.stab.response.BusinessException;
import org.garen.stab.util.file.FileUtil;
import org.garen.stab.util.file.FolderUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SerializeComponent {

    public <T> void saveT(String filePath, String split, T t) {
        validSavePath(filePath, split);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(t);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    public <T> T getT(String filePath) {
        if(!FileUtil.exists(filePath)){
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            return (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> void saveTS(String filePath, String split, List<T> ts) {
        validSavePath(filePath, split);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(ts);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validSavePath(String filePath, String split) {
        if(FileUtil.exists(filePath)){
            FileUtil.deleteFile(filePath);
        }
        try {
            FileUtil.assertFile(filePath, split);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public <T> List<T> getTS(String filePath) {
        if(!FileUtil.exists(filePath)){
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            List<T> list = (List<T>) ois.readObject();
            ois.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getFileNames(String folderPath, String split) {
        List<String> fileNames = new ArrayList<>();
        File[] files = FolderUtil.getFiles(folderPath, split);
        if(files != null && files.length > 0){
            for(File file : files){
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }

}
