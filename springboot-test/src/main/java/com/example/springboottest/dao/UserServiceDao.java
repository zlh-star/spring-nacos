package com.example.springboottest.dao;

import com.example.springboottest.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

@Mapper
public interface UserServiceDao extends ReactiveMongoRepository<UserModel,String> {
    Mono<UserModel> findByAccountName(String accountName);
    Mono<UserModel> deleteUserModelByAccountId(String accountId);
    Mono<UserModel> deleteUserModelByAccountName(String accountName);
}
