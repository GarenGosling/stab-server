package org.garen.stab.util.file;


import java.io.File;

public class FolderUtil {

    public static File assertFolder(String folderPath, String split){
        String parentFolderPath = PathUtil.getFolderPath(folderPath, split);
        FileUtil.validPathThrow(parentFolderPath);
        File folder = new File(folderPath);
        if(!folder.isDirectory()){
            folder.mkdir();
        }
        return folder;
    }

    public static void cleanFolder(String folderPath, String split) {
        File folder = assertFolder(folderPath, split);
        File[] files = folder.listFiles();
        if(files != null && files.length > 0){
            for(File file : files){
                file.delete();
            }
        }
    }

    public static File[] getFiles(String folderPath, String split) {
        assertFolder(folderPath, split);
        File folder = new File(folderPath);
        return folder.listFiles();
    }

//    public static void main(String[] args) {
//        String folderPath = "/Users/liuxueliang/Desktop/hello";
////        folderHandler.assertFolder(folderPath);
//        FolderUtil.cleanFolder(folderPath);
//    }

}
