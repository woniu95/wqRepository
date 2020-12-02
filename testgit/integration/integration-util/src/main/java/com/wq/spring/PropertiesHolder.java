package com.wq.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-02 16:05
 */
public class PropertiesHolder  extends PropertyPlaceholderConfigurer {

    /** Spring 配置中的所有参数 */
    private Properties wacProperties;

    /**
     * 重写处理参数的方法,取得配置参数供给系统取用
     * (non-Javadoc)
     * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        wacProperties = props;
    }

    public Properties getWacProperties() {
        return wacProperties;
    }

}