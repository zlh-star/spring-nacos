package com.example.dubboapi.service;

import reactor.core.publisher.Mono;

public interface HelloService{
    void hello(User user);
//    void insert(Person person);
//    Mono<Person> save(Person person);
}
