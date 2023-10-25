package com.example.springasync.service;

import java.util.concurrent.CompletableFuture;

public interface TestService {

    CompletableFuture<String> test(String a);
}
