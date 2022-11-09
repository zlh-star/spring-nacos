package com.example.cache.bo;

import org.springframework.data.redis.core.ZSetOperations;

public class Operations implements ZSetOperations.TypedTuple<String> {

    String id;
    double score;
    public Operations(String id,double score){
        this.id=id;
        this.score=score;
    }
    public Operations(){

    }

    @Override
    public String getValue() {
        return id;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public int compareTo(ZSetOperations.TypedTuple<String> o) {
        return 0;
    }
}
