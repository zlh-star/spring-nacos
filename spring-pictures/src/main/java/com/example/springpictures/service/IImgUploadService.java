package com.example.springpictures.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springpictures.mapper.Count;

public interface IImgUploadService extends IService<Count> {

    String imgUpload(String imgPath);

//    int insertImgPath(String imgPath);
}
