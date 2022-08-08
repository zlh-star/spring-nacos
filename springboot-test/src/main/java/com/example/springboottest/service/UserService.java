package com.example.springboottest.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboottest.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService extends IService<UserModel> {

    /**
     * 插入账户数据
     * @param userModel
     * @return
     */
    List<UserModel> insertAccount(UserModel userModel);

    /**
     * 根据账号ID对账户进行删除
     * @param accountId
     * @param userModelList
     * @return
     */
    List<UserModel> deleteAccount(@Param("accountId") String accountId, @Param("userModelList") List<UserModel> userModelList);

    /**
     * 更新账户
     * @param userModel
     * @return
     */
    List<UserModel> updateAccount(@Param("accountId") String accountId,@Param("userModel") UserModel userModel);

    /**
     * 通过账号ID查询账号信息
     * @param accountId
     * @return
     */
    List<UserModel> selectAccount(String accountId);
    /**
     * 获取所有的账号
     * @return
     */
    List<UserModel> getAllAccount();
}
