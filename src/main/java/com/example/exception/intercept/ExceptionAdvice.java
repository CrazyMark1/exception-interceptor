package com.example.exception.intercept;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/14 17:03
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExceptionAdvice {
}
