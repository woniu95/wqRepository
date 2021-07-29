package com.wq.eureka.client.context;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PackageName:com.wq.eureka.client
 * @ClassName:EurekaServerContext
 * @Description:
 * @author: wq
 * @date 2021/7/22 21:34
 */
@Component
public class EurekaClientContext {

    @Autowired
    private EurekaClient discoveryClient;

    public String serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("STORES", false);
        return instance.getHomePageUrl();
    }
}
