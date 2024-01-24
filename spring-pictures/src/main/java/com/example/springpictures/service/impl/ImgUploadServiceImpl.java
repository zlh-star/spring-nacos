package com.example.springpictures.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springpictures.mapper.Count;
import com.example.springpictures.mapper.ImageUploadMapper;
import com.example.springpictures.model.ImageUpload;
import com.example.springpictures.service.IImgUploadService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;


@Slf4j
@Service
public class ImgUploadServiceImpl extends ServiceImpl<ImageUploadMapper, Count> implements IImgUploadService {

    @Autowired
    private ImageUploadMapper imageUploadMapper;

    private static Logger logger = LoggerFactory.getLogger("ImgUploadServiceImpl.class");


    /**
     * 存储上传图片的路径
     * @param
     * @return
     */
    @Override
    public String imgUpload(List<Count> imgPaths){

        int rowCount=0;
        for(Count count:imgPaths ){
            ImageUpload upload = new ImageUpload();
            upload.setImgpath(count.getCout());
            // 一定要加非空判断，否则会报空指针异常
            if(upload.getImgpath() == null){
                return "图片地址为空";
            }
            log.info("向数据库中存储的路径为：" + upload.getImgpath());
            log.info("传递过来的imgPath参数为：" + count.getCout());
            log.info(upload.toString());
//            Count count1=new Count();
//            count1.setCout(upload.getImgpath());
            imageUploadMapper.insert(count);
            rowCount++;
        };
        log.info("上传总图片数为:"+rowCount);
        if(rowCount >=1){
            return "图片地址存储成功";
        }
        return "图片地址存储失败";
    }
//    public String imgUpload(String imgPath){
//
//        ImageUpload upload = new ImageUpload();
//        upload.setImgpath(imgPath);
//        // 一定要加非空判断，否则会报空指针异常
//        if(upload.getImgpath() == null){
//            return "图片地址为空";
//        }
//        logger.info("向数据库中存储的路径为：" + upload.getImgpath());
//        logger.info("传递过来的imgPath参数为：" + imgPath);
//        logger.info(upload.toString());
//        Count count=new Count();
//        count.setCout(upload.getImgpath());
//        int rowCount = imageUploadMapper.insert(count);
//        logger.info(rowCount + "");
//        if(rowCount > 0){
//            return "图片地址存储成功";
//        }
//        return "图片地址存储失败";
////        return "success";
//    }


}
