package com.lpy.error;

import lombok.Data;

@Data
public class ErrorInfo<T> {
    public static final Integer SUCCESS = 200;
    public static final Integer ERROR = 100;
    //错误信息
    private String message;
    //错误码
    private Integer code;
    private String url;
    private T data;
}
