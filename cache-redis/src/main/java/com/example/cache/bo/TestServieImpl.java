package com.example.cache.bo;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TestServieImpl implements UserService {

    private static final Map<String,Test> testMap=new ConcurrentHashMap<>();

    @CachePut(value = "Test",keyGenerator= "idGenerator")
    @Override
    public Test save(Test test) {
        return testMap.put(test.getId(),test);
    }

    @Override
    public void saveTest(List<Test> testList) {

    }
}
