package com.example.springbootcamel.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Cacheable("books")
    public String getBook(String abc) throws InterruptedException {
        return findBookInSlowSource(abc);
    }

    private String findBookInSlowSource(String abc) throws InterruptedException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return "zhaolinhai";
    }
}
