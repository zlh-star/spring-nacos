package com.example.springpictures.config;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     *
     * @param file
     * @param realPath
     * @param newName
     * @throws Exception
     */

    public static void upload(MultipartFile file, String realPath, String newName) throws Exception{
        File folder = new File(realPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
//        FileOutputStream out = new FileOutputStream(folder+newName);
//        out.write(file.getBytes());
//        out.flush();
//        out.close();

        file.transferTo(new File(folder,newName));

    }
//    public static void upload(byte[] file, String filePath, String fileName) throws Exception{
//        File targetFile = new File(filePath);
//        if(!targetFile.exists()){
//            targetFile.mkdirs();
//        }
//
//        FileOutputStream out = new FileOutputStream(filePath+fileName);
//        out.write(file);
//        out.flush();
//        out.close();
//
////        // 生成新的文件名
////        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);
////
////        //使用原文件名
////        String realPath = path + "/" + fileName;
////
////        File dest = new File(realPath);
////
////        //判断文件父目录是否存在
////        if(!dest.isDirectory()){
////            dest.mkdirs();
////        }
////
////        try {
////            //保存文件
////            file.transferTo(dest);
////            return true;
////        } catch (IllegalStateException e) {
////            e.printStackTrace();
////            return false;
////        } catch (IOException e) {
////            e.printStackTrace();
////            return false;
////        }
//
//    }


}
