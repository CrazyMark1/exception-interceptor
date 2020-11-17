package com.example.exception.intercept;

import java.lang.annotation.*;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/13 21:17
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionIntercept {
}
