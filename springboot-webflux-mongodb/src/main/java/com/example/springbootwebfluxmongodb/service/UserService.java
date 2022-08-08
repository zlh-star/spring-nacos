package com.example.springbootwebfluxmongodb.service;

import com.example.springbootwebfluxmongodb.mapper.User;
import com.example.springbootwebfluxmongodb.mapper.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

     //因name为unique所以要进行错误处理，防止因为重复而出错
    public Mono<User> save(User user)
    {
        return userRepository.save(user)
        .onErrorResume(e ->userRepository.findUserByName(user.getName())//找到name的重复记录
            .flatMap(originalUser ->{
                user.setId(originalUser.getId());
                return userRepository.save(user); //拿到ID从而进行更新
            }));
   }

   public Mono<User> findUserByName(String name){
        return userRepository.findUserByName(name);
   }

   public Mono<User> deleteUserById(String id){
        return userRepository.deleteUserById(id);
   }

   public Flux<User> findAll(){
        return userRepository.findAll();
   }

   public Mono<User> findById(String id){
        return userRepository.findById(id);
   }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> deleteByUsername(String name){
        return  userRepository.deleteUserByName(name);
    }
}
