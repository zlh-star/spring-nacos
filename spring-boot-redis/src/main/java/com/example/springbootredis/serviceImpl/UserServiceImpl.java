package com.example.springbootredis.serviceImpl;

import com.example.springbootredis.service.User;
import com.example.springbootredis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserService userService;
    private static final Map<Long,User> userMap=new HashMap<>();

//    static {
//        userMap.put(1L,new User(1L,"zhao",18));
//        userMap.put(2L,new User(2l,"lin",19));
//        userMap.put(3L,new User(3L,"hai",20));
//    }
    @CachePut(value ="user",keyGenerator= "idGenerator")
    @Override
    public User save(User user) {
        userMap.put(user.getId(),user);
        log.info("存储对象",user);
        return user;
    }

    @Cacheable(value = "user",keyGenerator= "idGenerator")
    @Override
    public User getUser(Long id) {
        log.info("获取当前对象",userMap.get(id));
        return userMap.get(id);
    }

    @CacheEvict(value = "user",keyGenerator= "idGenerator")
    @Override
    public void delete(User user) {
        userMap.remove(user);
        log.info("进入删除，删除成功");
    }
}
