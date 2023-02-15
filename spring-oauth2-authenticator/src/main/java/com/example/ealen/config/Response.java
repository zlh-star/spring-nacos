package com.example.ealen.config;

import lombok.Data;

@Data
public class Response<T> {

    private int code;
    private String message;
    private Object data;
    private long total;
    private transient T t;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Response(){
        super();
    }

    public Response(Object data){
        super();
        this.data=data;
    }

    public Response(String message, Object data){
        super();
        this.data=data;
        this.message=message;
    }

    public Response(int code, Object data, String message){
        super();
        this.data=data;
        this.code=code;
        this.message=message;
    }
    public Response(T t, int code, String message){
        super();
        this.t=t;
        this.code=code;
        this.message=message;
    }
    public Response(T t, int code){
        super();
        this.t=t;
        this.code=code;
    }
}
