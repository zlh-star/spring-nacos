package com.example.provider.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private transient T data;
    private long total;

    public ResponseResult() {}

    public ResponseResult(T data) {
        this(ResponseConstant.SUCCESS, "success", data);
    }

    public ResponseResult(String code, String message) {
        this(code, message, (T) null);
    }

    public ResponseResult(String code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
        resetTotal();
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult(data);
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult((Object)null);
    }

    public static <T> ResponseResult<T> fail(String code, String message, T data) {
        return new ResponseResult(code, message, data);
    }

    public static <T> ResponseResult<T> fail(String code, String message) {
        return new ResponseResult(code, message);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 重新设置返回数据total值
     */
    private void resetTotal(){
        if(this.total==0 && this.data!=null){
            try{
                Map map= (Map)this.data;
                this.total=map.size();
            }catch(ClassCastException e1){
                try{
                    List list= (List)this.data;
                    this.total=list.size();
                }catch(ClassCastException e2){
                    this.total=1;//返回的其他对象默认条数为1
                }
            }
        }
    }
}