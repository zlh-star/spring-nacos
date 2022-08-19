package com.example.springvarification.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuService extends BaseMapper<MenuBo> {
}
