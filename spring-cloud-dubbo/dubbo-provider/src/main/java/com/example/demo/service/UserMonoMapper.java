package com.example.demo.service;

import com.example.dubboapi.service.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserMonoMapper extends ReactiveMongoRepository<Person,String> {
    Mono<Person> findPersonByName(String name);
}
