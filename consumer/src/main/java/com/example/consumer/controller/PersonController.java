package com.example.consumer.controller;

import com.example.consumer.service.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private Person person;

    @RequestMapping(value = "/ini/{name}",method = RequestMethod.POST)
    public String index(@PathVariable("name") String name){
        String test=person.person(name);
        System.out.println(test);
        return person.person(name);
    }

    @RequestMapping(value = "/getScanner",method = RequestMethod.GET)
    public Object getScanner(){
        System.out.println(person.getScanner());
        return person.getScanner();
    }

    @RequestMapping(value = "/testRow",method = RequestMethod.POST)
    public String testRow(){

        System.out.println(person.testRow());
        return person.testRow();
    }

    @RequestMapping(value = "/testRows",method = RequestMethod.POST)
    public String testRows(){

        System.out.println(person.testRows());
        return person.testRows();
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){

        System.out.println(person.test());
        return person.test();
    }

    @RequestMapping(value = "/deleteColumnFamily",method = RequestMethod.DELETE)
    public String deleteColumnFamily(){

        System.out.println(person.deleteColumnFamily());
        return person.deleteColumnFamily();
    }

    @RequestMapping(value = "/deleteTable",method = RequestMethod.DELETE)
    public String deleteTable(){

        System.out.println(person.deleteTable());
        return person.deleteTable() ;
    }
}
