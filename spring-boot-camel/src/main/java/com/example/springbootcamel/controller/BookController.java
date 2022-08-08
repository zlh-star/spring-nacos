package com.example.springbootcamel.controller;

import com.example.springbootcamel.service.BookService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/ceshi")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/{abc}")
    public String ceshi(@PathVariable("abc") String abc) throws InterruptedException {
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        String result=bookService.getBook(abc);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return result;
    }
}
