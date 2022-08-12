package com.example.springeasyexcelinput.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springeasyexcelinput.dto.UserDto;

import javax.xml.transform.Result;
import java.util.List;

public interface UserMapper extends BaseMapper<UserDto> {
//    Result importExcel(MultipartFile file);
    void insertDto(List<UserDto> userDtoList);
}
