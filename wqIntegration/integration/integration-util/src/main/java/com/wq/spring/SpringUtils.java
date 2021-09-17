package com.wq.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 获取spring组件的工具类
 */
@Service
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    /**
     * 获取方法参数名称 不支持接口
     * 如要支持接口  编译需增加-parameters 参数
     * @param method
     * @return
     */
    public static List<String> getMethodParamNames(Method method) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();

        //获取到该方法的参数们
        String[] params = u.getParameterNames(method);
        return Arrays.asList(params);
    }

}
