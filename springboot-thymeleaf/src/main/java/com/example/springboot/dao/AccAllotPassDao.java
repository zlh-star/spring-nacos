package com.example.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccAllotPassDao {
    public void AccAllotPass (@Param("accAllotPass") List<AccAllotPassBo> accAllotPass);
    public void delAccountByid(@Param("id") String id,@Param("account")List<String> account);

    List<String> getAll(@Param("id") String id);
    List<AccAllotPassBo> updateAccountUserById(@Param("account") String account,@Param("password") String password,
                                       @Param("id") String id,@Param("shortname") String shortname,@Param("fullname") String fullname);

}
