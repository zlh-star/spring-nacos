package com.example.dubboapi.config;

public class Results {
    public Results() {
    }

    public static <T> ResponseResult<T> wrapResult(T t) {
        return new ResponseResult(t);
    }

    public static <T> ResponseResult<T> wrapResult(T t, int total) {
        ResponseResult<T> rs = new ResponseResult();
        rs.setCode(ResponseConstant.SUCCESS);
        rs.setData(t);
        rs.setTotal((long)total);
        return rs;
    }

    public static <T> ResponseResult<T> wrapResult(String code, String message) {
        return new ResponseResult(code, message);
    }
}

