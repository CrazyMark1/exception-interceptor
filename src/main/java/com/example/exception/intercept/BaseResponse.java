package com.example.exception.intercept;

import lombok.Data;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/13 21:52
 * @Description:
 */
@Data
public class BaseResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
