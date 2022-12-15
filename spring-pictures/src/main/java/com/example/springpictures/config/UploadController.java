package com.example.springpictures.config;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@Configuration
@Slf4j
public class UploadController {

    @Value("${server.port}")
    private String port;

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

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public Response upload(@RequestParam(value = "files",required = false) MultipartFile[] files, Response response) {
        log.info("上传多个文件");
        StringBuilder builder = new StringBuilder();
        // file address
        String fileAddress ="http://"+ getIp()+ ":" + port + File.separator;

        ArrayList<String> imgUrls = new ArrayList<String>();
        try {
            for (int i = 0; i < files.length; i++) {
                // old file name
                String fileName = files[i].getOriginalFilename();
                // new filename
                String generateFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
                // store filename
                String distFileAddress = fileAddress + generateFileName;
                builder.append(distFileAddress+",");
                imgUrls.add(distFileAddress);
                // generate file to disk
                files[i].transferTo(new File(getFileDir() + generateFileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setMsg("success");
        log.info(builder.toString());
        response.setData(imgUrls);
        return response;
    }

    public static String fileDir;

    public static String getFileDir() {
        fileDir = "/Users/wz/projects/blog/uploadFile/";
        return fileDir;
    }
}
