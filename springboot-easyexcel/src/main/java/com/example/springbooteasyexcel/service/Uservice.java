package com.example.springbooteasyexcel.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbooteasyexcel.dao.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Uservice extends BaseMapper<User> {
    public List<User> getAllUser();
}
