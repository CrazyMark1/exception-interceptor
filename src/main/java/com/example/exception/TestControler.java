package com.example.exception;

import com.example.exception.intercept.BaseResponse;
import com.example.exception.intercept.ExceptionIntercept;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/14 16:21
 * @Description:
 */
@RestController
public class TestControler {

    @RequestMapping("/test")
    @ExceptionIntercept
    public BaseResponse test(@RequestParam("a") Integer a){
        if (a == 1){
            return new BaseResponse(1,a+"");
        }
        else if (a == 2){
            throw new NullPointerException();
        }
        else throw new RuntimeException();

    }
}
