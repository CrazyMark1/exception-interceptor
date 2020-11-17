package com.example.exception.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/13 21:45
 * @Description:
 */
@Component
public class ExceptionProcessor {
    @Autowired
    private ExceptionMethodPool exceptionMethodPool;

    public BaseResponse process(Throwable e) {
        return (BaseResponse) FunctionUtil.computeOrGetDefault(() ->{
            Method method = exceptionMethodPool.obtainMethod(e);
            method.setAccessible(true);
            return method.invoke(exceptionMethodPool.getExcutor(),e);
        },new BaseResponse(0,"未知错误"));
    }
}
