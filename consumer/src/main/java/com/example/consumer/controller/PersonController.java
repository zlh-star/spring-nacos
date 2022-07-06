package com.example.consumer.controller;

import com.example.consumer.service.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    private Person person;

    @RequestMapping("/ini/{name}")
    public String index(@PathVariable("name") String name){
        String test=person.person(name);
        System.out.println(test);
        return person.person(name);
    }
}
