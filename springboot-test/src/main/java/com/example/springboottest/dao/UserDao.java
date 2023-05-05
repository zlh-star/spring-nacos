package com.example.springboottest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboottest.model.UserCondition;
import com.example.springboottest.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<UserModel> {
    Page<UserModel> page(@Param("page") Page<UserModel> page);
    /**
     * 插入账户数据
     * @param userModel
     * @return
     */
    int insertAccount(@Param("userModel") List<UserModel> userModel);

    /**
     * 根据账号ID对账户进行删除
     * @param accountId
     * @param userModelList
     * @return
     */
    int deleteAccount(@Param("accountId") String accountId, @Param("userModelList") List<UserModel> userModelList);

    /**
     * 更新账户
     * @param userModel
     * @return
     */
    int updateAccount(@Param("userModel") List<UserModel> userModel);

    int update(@Param("userModel") UserModel userModel);

    /**
     * 通过账号ID查询账号信息
     * @param accountId
     * @return
     */
    List<UserModel> selectAccount(String accountId);

    /**
     * 对账号进行分页
     * @param userCondition
     * @return
     */
    List<UserModel> selectAllAccount(@Param("userCondition") UserCondition userCondition);

    /**
     * 获取所有的账号
     * @return
     */
    List<UserModel> getAllAccount();

    /**
     * 根据账号Id对账号进行分页
     * @param accountId
     * @return
     */
    public int getAllAccount(Map<UserModel,Object> accountId);

}
