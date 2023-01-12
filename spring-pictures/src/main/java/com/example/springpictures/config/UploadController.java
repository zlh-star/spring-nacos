package com.example.springpictures.config;

import com.example.springpictures.service.IImgUploadService;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@Configuration
@Slf4j
public class UploadController {

    @Value("${server.port}")
    private String port;

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    private IImgUploadService iImgUploadService;

//    @Value("${server.port}")
//    private String port;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public Object upload(@RequestBody MultipartFile file, HttpServletRequest request) {

//        List<MultipartFile> files = new ArrayList<>(Arrays.asList(file));
        log.info("上传多个文件");
//        for (MultipartFile file1 : file) {
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            String filePath = request.getSession().getServletContext().getRealPath(path);
            log.info("filename1:" + fileName);
            log.info("filePath1:" + filePath);

            try {
                FileUtils.upload(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 拼接图片url
            String imgHost = "http://" + getIp() + ":" + port;
            String imgUploadPath = path;
            String imgName =
//                    UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
                    fileName;
            String imgPath = imgHost + imgUploadPath + imgName;

            log.info("拼接好的图片上传路径为：" + imgPath);

            return Result.wrapResult("upload img success，请到上传路径查看！" + iImgUploadService.imgUpload(imgPath));
        }


    //获取当前IP地址
    public String getIp() {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return localhost.getHostAddress();
    }
//        return 0;
    }
//        log.info("上传多个文件");
//        StringBuilder builder = new StringBuilder();
//        // file address
//        String fileAddress ="http://"+ getIp()+ ":" + port + File.separator;
//
//        ArrayList<String> imgUrls = new ArrayList<String>();
//
//        try {
////            for (int i = 0; i < files.length; i++) {
//                // old file name
//                String fileName = files.getOriginalFilename();
//                // new filename
//                String generateFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
//                // store filename
//                String distFileAddress = fileAddress + generateFileName;
//                builder.append(distFileAddress+",");
//                imgUrls.add(distFileAddress);
//                // generate file to disk
//                files.transferTo(new File(getFileDir() + generateFileName));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        response.setMsg("success");
//        log.info(builder.toString());
//        response.setData(imgUrls);
//        return response;
//    }
//
//    public static String fileDir;
//
//    public static String getFileDir() {
//        fileDir = "E:\\picture";
//        return fileDir;
//    }


