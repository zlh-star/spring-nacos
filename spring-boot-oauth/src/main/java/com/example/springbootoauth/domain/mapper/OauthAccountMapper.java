package com.example.springbootoauth.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootoauth.domain.entity.OauthAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OauthAccountMapper extends BaseMapper<OauthAccount> {

    /**
     * 获取客户端用户信息
     *
     * @param username 用户名
     * @return 用户对象
     */
    OauthAccount loadUserByUsername(@Param("clientId") String clientId,@Param("username") String username);

}
