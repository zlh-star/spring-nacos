package com.example.springeasyexcelinput.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springeasyexcelinput.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDto> {
//    Result importExcel(MultipartFile file);
    @Async
    void insertDto(List<UserDto> userDtoList);
}
