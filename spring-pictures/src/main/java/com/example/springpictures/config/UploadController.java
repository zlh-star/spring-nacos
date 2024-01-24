package com.example.springpictures.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springpictures.mapper.Count;
import com.example.springpictures.service.IImgUploadService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Configuration
@Slf4j
public class UploadController {

    @Value("${file-save-path}")
    private String fileSavePath;

    @Autowired
    private IImgUploadService iImgUploadService;

    @Autowired
    private IImgUploadService imgUploadService;

    private final String formDate=new SimpleDateFormat("/yyyy.MM.dd/").format(new Date());



//    @Value("${server.port}")
//    private String port;

    @ApiOperation(value= "/upload", tags = "上传多个文件")
    @RequestMapping(value = "/upload", consumes = {"multipart/form-data"}
            ,method = RequestMethod.POST)
    public Object upload(@RequestBody List<MultipartFile> file, HttpServletRequest request) {

        List<Count> imgPaths=new ArrayList<>();
        Map<String,Object> result=new HashMap<>();
        log.info("上传多个文件");
        for (MultipartFile multipartFile : file) {
            String contentType = multipartFile.getContentType();
            String fileName = multipartFile.getOriginalFilename();

            if(reName(multipartFile)==null&&fileName!=null){
                if(!fileName.endsWith(".jpg")&&!fileName.endsWith(".png")&&!fileName.endsWith(".jpeg")){
                    result.put("status","error");
                    result.put("message","文件类型错误");
                    return result;
                }
                String[] a=fileName.split("\\.");
                String realPath=fileSavePath+formDate;
                String newName= UUID.randomUUID() +"."+a[1];
                try {
                    FileUtils.upload(multipartFile, realPath, newName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 拼接图片url
                String imgUrl = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort()
                        +"/images"+formDate+newName;
                Count count=new Count();
                count.setCout(imgUrl);
                count.setFileName(fileName);
                imgPaths.add(count);
            }
        }
        return Result.wrapResult("upload img success，请到上传路径查看！"+ iImgUploadService.imgUpload(imgPaths));
    }

    private String reName(MultipartFile multipartFile){

        String fileName = multipartFile.getOriginalFilename();
        LambdaQueryWrapper<Count> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Count::getFileName,fileName);

        Count count =imgUploadService.getOne(queryWrapper);
//        if(count==null){
//            return null;
//        }
//        String oldName= count.getFileName()==null?null:fileName;
//        System.out.println(oldName);
        return count==null ? null : count.getFileName();
    }


    //获取当前IP地址
    public String getIp() {
        InetAddress localhost = null;
        String host = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        
//        return localhost.getHostAddress();
        if(localhost!=null){
            host=localhost.getHostAddress();
        }
        return host;
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


