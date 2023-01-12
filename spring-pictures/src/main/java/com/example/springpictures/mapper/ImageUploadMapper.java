package com.example.springpictures.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springpictures.model.ImageUpload;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageUploadMapper extends BaseMapper<Count> {
    int insert(ImageUpload record);

    int insertSelective(ImageUpload record);

    int insertImgPath(String imgPath);

}