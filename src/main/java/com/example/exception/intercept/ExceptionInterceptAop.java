package com.example.exception.intercept;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/13 21:20
 * @Description:
 */
@Aspect
@Component
public class ExceptionInterceptAop {
    @Autowired
    private ExceptionProcessor exceptionProcessor;

    @Pointcut("@annotation(com.example.exception.intercept.ExceptionIntercept)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        return computeAndDealException(() -> point.proceed(),
                e -> exceptionProcessor.process(e));
    }


    public static <R> R computeAndDealException(ThrowExceptionSupplier<R> supplier, Function<Throwable, R> dealFunc) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            return dealFunc.apply(e);
        }
    }


    @FunctionalInterface
    public interface ThrowExceptionSupplier<T> {
        T get() throws Throwable;
    }
}
