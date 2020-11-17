package com.example.exception.intercept;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/14 17:18
 * @Description:
 */
public class ExceptionMethodPool {
    private List<ExceptionMethod> methods;
    private Object excutor;

    public ExceptionMethodPool(Object excutor) {
        this.methods = new ArrayList<ExceptionMethod>();
        this.excutor = excutor;
    }

    public Object getExcutor() {
        return excutor;
    }

    public void add(Class<? extends Throwable> clazz, Method method) {
        methods.add(new ExceptionMethod(clazz, method));
    }

    public Method obtainMethod(Throwable throwable) {
        return methods
                .stream()
                .filter(e -> e.getClazz().isAssignableFrom(throwable.getClass()))
                .findFirst()
                .orElseThrow(() ->new RuntimeException("没有找到对应的异常处理器"))
                .getMethod();
    }

    @AllArgsConstructor
    @Getter
    class ExceptionMethod {
        private Class<? extends Throwable> clazz;
        private Method method;
    }
}
