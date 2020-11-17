package com.example.exception.intercept;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/14 17:08
 * @Description:
 */
@ExceptionAdvice
public class ExceptionConfig {
    @ExceptionHandler(value = NullPointerException.class)
    public BaseResponse process(NullPointerException e){
        return new BaseResponse(0,"NPE");
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResponse process(Exception e){
        return new BaseResponse(0,"Ex");
    }

}
