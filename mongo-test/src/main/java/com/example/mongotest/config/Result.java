package com.example.mongotest.config;

public class Result {
    public Result() {
    }
    public static <T> Response<T> wrapResult(T t) {
        return new Response<T>(t);
    }

    public static <T> Response<T> wrapResult(T t, int total) {
        Response<T> rs = new Response<T>();
        rs.setCode(Contants.CODESUCCESS);
        rs.setData(t);
        rs.setTotal(total);
        return rs;
    }

    public static <T>  Response<T> wrapResult(int code, String message,T t) {
        return new Response<T>(t,code, message);
    }
    public static <T>  Response<T> wrapResult(Object data, String message) {
        return new Response<T>(message,data);
    }

}
