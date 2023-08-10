package com.example.redisdelaydelete.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.redisdelaydelete.mapper.User;
import com.example.redisdelaydelete.mapper.UserMapper;
import com.example.redisdelaydelete.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public int insert(User user) {

        String key="user_"+user.getAge();
        int a=userMapper.insert(user);
        if(a>0){
            redisTemplate.delete(key);
        }
        return a;
    }

    @Override
    public int update(User user) throws InterruptedException {
        String key = "user_" + user.getAge();
        Boolean hasKey = redisTemplate.hasKey(key);
        if(Boolean.TRUE.equals(hasKey)){
            redisTemplate.delete(key);
        }
        int update = userMapper.update(user,null);
        Thread.sleep(500);
        if( update > 0 ){
            // 缓存存在，进行删除
            if (Boolean.TRUE.equals(hasKey)) {
                redisTemplate.delete(key);
                System.out.println("StudentServiceImpl.update() : 从缓存中删除学生 >> " + user.toString());
            }

        }
        return update;
    }

    @Override
    public User getUser(String age) {
        // 从缓存中 取出学生信息
        String key = "user_" + age;
        Boolean hasKey = redisTemplate.hasKey(key);

        ValueOperations operations = redisTemplate.opsForValue();
        // 缓存存在
        if (hasKey) {
            String str = (String) operations.get(key);
//            User student = new Gson().fromJson(str, User.class);
            User user= JSON.parseObject(str,User.class);
            return user;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper
                .isNotNull(User::getNickName)
                .ge(User::getAge, age);
        User user =userMapper.selectOne(queryWrapper) ;
//        String str = new Gson().toJson(student);
        String str= JSON.toJSONString(user);
        // 插入缓存中
        operations.set(key, str, 1000, TimeUnit.DAYS);
        return user;
    }
}
