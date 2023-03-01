package com.example.dubboapi.service;

import reactor.core.publisher.Mono;

public interface MonoService {
    void findPersonByName(String id);
}
