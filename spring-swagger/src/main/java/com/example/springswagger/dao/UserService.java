package com.example.springswagger.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveMongoRepository<User,String> {
    Mono<User> findUserById(String id);
    Mono<User> deleteUserById(String id);
    Mono<User> deleteAllByNickName(String nickName);
}
