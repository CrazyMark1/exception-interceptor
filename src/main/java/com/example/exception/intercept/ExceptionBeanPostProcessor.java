package com.example.exception.intercept;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Auther: 云烨（刘锴）
 * @Date: 2020/11/14 17:15
 * @Description:
 */
@Component
public class ExceptionBeanPostProcessor implements BeanPostProcessor {
    private ExceptionMethodPool exceptionMethodPool;
    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        ExceptionAdvice advice = clazz.getAnnotation(ExceptionAdvice.class);
        if (advice == null) return bean;
        if (exceptionMethodPool != null) throw new RuntimeException("不允许有两个异常定义类");
        exceptionMethodPool = new ExceptionMethodPool(bean);

        //保持处理异常方法顺序
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getAnnotation(ExceptionHandler.class) != null)
                .forEach(method -> {
                    ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
                    Arrays.stream(exceptionHandler.value()).forEach(c -> exceptionMethodPool.add(c,method));
                });
        //注册
        context.getBeanFactory().registerSingleton("exceptionMethodPool",exceptionMethodPool);
        return bean;
    }
}
