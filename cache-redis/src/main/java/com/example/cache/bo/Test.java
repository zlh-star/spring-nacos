package com.example.cache.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test implements Serializable {
    private String id;
    private String name;
    private String core;
}
