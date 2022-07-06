package com.example.springswagger.service.Impl;


import com.example.springswagger.dao.User;
import com.example.springswagger.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class UserServiceImpl {

    @Autowired
    UserService userService;

    public Mono<User> save(User user){
        return userService.save(user)
                .onErrorResume(e->
                        userService.findUserById(user.getId())
                .flatMap(originalUser->{
                    user.setId(originalUser.getId());
                    return userService.save(user);
                }));
    }

//    public Flux<User> findAll(){
////        Flux<User> list=
//        return userService.findAll();
//    }

}
