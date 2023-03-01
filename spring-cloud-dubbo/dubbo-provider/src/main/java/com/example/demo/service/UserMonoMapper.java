package com.example.demo.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMonoMapper extends ReactiveMongoRepository<Person,String> {
}
