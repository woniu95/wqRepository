package com.wq.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-02 16:04
 */
public class PropertyUtil {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private static PropertiesHolder spc;

    private PropertiesHolder springProperties;


    public void initializer() throws Exception {
        logger.debug("========================ConstantInitConstantInit系统启动时进行初始化处理========================" + springProperties.toString());
        if( springProperties != null){
            spc = springProperties;
        }
        logger.debug("========================ConstantInitConstantInit初始化完成========================");
    }

    public static String getConfigVal(String key){
        if(spc != null){
            return spc.getWacProperties().getProperty(key);
        }
        return null;
    }

    public void setSpringProperties(PropertiesHolder springProperties) {
        this.springProperties = springProperties;
    }
}
