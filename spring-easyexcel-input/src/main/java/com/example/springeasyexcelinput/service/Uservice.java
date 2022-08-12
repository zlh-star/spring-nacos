package com.example.springeasyexcelinput.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springeasyexcelinput.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.IOException;
import java.util.List;

@Mapper
public interface Uservice extends BaseMapper<UserDto> {
//    void importExcel(MultipartFile file) throws IOException;
void insertDto(UserDto userDto);
}
