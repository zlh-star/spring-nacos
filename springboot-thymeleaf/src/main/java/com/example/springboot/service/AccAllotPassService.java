package com.example.springboot.service;

import com.example.springboot.dao.AccAllotPassBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccAllotPassService {
    public int AccAllotPass (@Param("accAllotPass") List<AccAllotPassBo> accAllotPass);
    public void delAccountByid(@Param("id") String id,@Param("account")List<String> account);
}
