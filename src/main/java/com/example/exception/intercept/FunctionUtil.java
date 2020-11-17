package com.example.exception.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/5 10:46
 * @Description:
 */
public class FunctionUtil {
    static Logger log = LoggerFactory.getLogger(FunctionUtil.class.getName());

    /**
     * 赋予方法重试能力
     *
     * @param runnable
     * @param time
     */
    public static void retryFunction(ThrowExceptionRunnable runnable, int time) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (Exception e) {
                time--;
                if (time <= 0) throw new RuntimeException(e);
            }
        }
    }

    /**
     * 赋予函数重试的能力
     *
     * @param function
     * @param t
     * @param time
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R retryFunction(ThrowExceptionFunction<T, R> function, T t, int time) {
        while (true) {
            try {
                return function.apply(t);
            } catch (Exception e) {
                time--;
                if (time <= 0) throw new RuntimeException(e);
            }
        }
    }

    public static <T, U, R> R retryFunction(ThrowExceptionBiFunction<T, U, R> function, T t, U u, int time) {
        while (true) {
            try {
                return function.apply(t, u);
            } catch (Exception e) {
                time--;
                if (time <= 0) throw new RuntimeException(e);
            }
        }
    }


    /**
     * 赋予函数缓存能力
     *
     * @param function
     * @param t
     * @param cache
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R cacheFunction(Function<T, R> function, T t, Map<T, R> cache) {
        R r = cache.get(t);
        if (r != null) return r;
        R result = function.apply(t);
        cache.put(t,result);
        return result;
    }


    /**
     * 赋予函数报错返回默认值能力
     *
     * @param function
     * @param t
     * @param r
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R computeOrGetDefault(ThrowExceptionFunction<T, R> function, T t, R r) {
        try {
            return function.apply(t);
        } catch (Exception e) {
            return r;
        }
    }

    public static <R> R computeOrGetDefault(ThrowExceptionSupplier<R> supplier,R r){
        try {
            return supplier.get();
        } catch (Exception e) {
            return r;
        }
    }


    /**
     * 赋予函数处理异常能力
     *
     * @param function
     * @param t
     * @param dealFunc
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R computeAndDealException(ThrowExceptionFunction<T, R> function, T t, Function<Exception, R> dealFunc) {
        try {
            return function.apply(t);
        } catch (Exception e) {
            return dealFunc.apply(e);
        }
    }

    /**赋予函数处理异常能力
     *
     * @param function
     * @param t
     * @param u
     * @param dealFunc
     * @param <T>
     * @param <U>
     * @param <R>
     * @return
     */
    public static <T, U, R> R computeAndDealException(ThrowExceptionBiFunction<T,U, R> function, T t, U u,Function<Exception, R> dealFunc) {
        try {
            return function.apply(t,u);
        } catch (Exception e) {
            return dealFunc.apply(e);
        }
    }

    /**
     * 赋予函数处理异常能力
     * @param supplier
     * @param dealFunc
     * @param <R>
     * @return
     */
    public static <R> R computeAndDealException(ThrowExceptionSupplier<R> supplier, Function<Exception, R> dealFunc) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return dealFunc.apply(e);
        }
    }


    /**
     * 赋予函数记录日志能力
     *
     * @param function
     * @param t
     * @param logTitle
     * @param <T>
     * @param <R>
     */
    public static <T, R> R logFunction(Function<T, R> function, T t, String logTitle) {
        long startTime = System.currentTimeMillis();
        log.info("[[title={}]],request={},requestTime={}", logTitle, t.toString(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        R apply = function.apply(t);
        long endTime = System.currentTimeMillis();
        log.info("[[title={}]],response={},spendTime={}ms", logTitle, apply.toString(), endTime - startTime);
        return apply;
    }



    @FunctionalInterface
    public interface ThrowExceptionFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowExceptionBiFunction<T, U, R> {
        R apply(T t, U u) throws Exception;
    }
    @FunctionalInterface
    public interface ThrowExceptionSupplier<T> {
        T get() throws Exception;
    }
    @FunctionalInterface
    public interface ThrowExceptionRunnable {
        void run() throws Exception;
    }

}
