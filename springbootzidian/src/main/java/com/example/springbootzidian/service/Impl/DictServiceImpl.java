package com.example.springbootzidian.service.Impl;

import com.example.springbootzidian.service.DictService;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    RedisTemplate redisTemplate;

    private static Logger logger=Logger.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    public String transForm(String name, int value) {
        String tmp=redisTemplate.opsForValue().get(name+"_"+value).toString();
        if(StringUtils.isNotBlank(tmp)){
            return tmp;
        }
        return String.valueOf(value);
    }
}
