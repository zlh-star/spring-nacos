package com.example.springpictures.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springpictures.mapper.Count;

import java.util.List;

public interface IImgUploadService extends IService<Count> {

    String imgUpload(List<Count> imgPaths);

//    int insertImgPath(String imgPath);
}
