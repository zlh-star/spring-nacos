package com.example.springboottest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboottest.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao extends BaseMapper<Student> {
}
