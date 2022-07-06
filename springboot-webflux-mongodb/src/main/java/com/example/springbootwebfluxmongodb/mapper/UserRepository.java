package com.example.springbootwebfluxmongodb.mapper;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,String> {

    Mono<User> findUserByName(String name);
    Mono<User> deleteUserById(String id);
    Mono<User> deleteUserByName(String name);

}
