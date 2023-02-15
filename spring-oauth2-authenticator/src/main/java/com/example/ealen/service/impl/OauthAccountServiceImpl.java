package com.example.ealen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ealen.domain.entity.OauthAccount;
import com.example.ealen.domain.mapper.OauthAccountMapper;
import com.example.ealen.service.OauthAccountService;
import org.springframework.stereotype.Service;

@Service
public class OauthAccountServiceImpl extends ServiceImpl<OauthAccountMapper,OauthAccount> implements OauthAccountService {
}
