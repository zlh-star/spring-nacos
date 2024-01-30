package com.example.provider.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMonoMapper extends ReactiveMongoRepository<Person,String> {
}
