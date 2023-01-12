package com.example.springpictures.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springpictures.mapper.Count;
import com.example.springpictures.mapper.ImageUploadMapper;
import com.example.springpictures.model.ImageUpload;
import com.example.springpictures.service.IImgUploadService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;


@Service("iImgUploadService")
@MapperScan("com.example.springpictures.service")
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
    public String imgUpload(String imgPath){

        ImageUpload upload = new ImageUpload();
        upload.setImgpath(imgPath);
        // 一定要加非空判断，否则会报空指针异常
        if(upload.getImgpath() == null){
            return "图片地址为空";
        }
        logger.info("向数据库中存储的路径为：" + upload.getImgpath());
        logger.info("传递过来的imgPath参数为：" + imgPath);
        logger.info(upload.toString());
        Count count=new Count();
        count.setCout(upload.getImgpath());
        int rowCount = imageUploadMapper.insert(count);
        logger.info(rowCount + "");
        if(rowCount > 0){
            return "图片地址存储成功";
        }
        return "图片地址存储失败";
//        return "success";
    }


}
