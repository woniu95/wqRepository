package com.wq.eureka.client.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @PackageName:com.wq.eureka.client
 * @ClassName:DiscoveryClientContext
 * @Description:
 * @author: wq
 * @date 2021/7/22 21:39
 */
@Component
public class DiscoveryClientContext {
    @Autowired
    private DiscoveryClient discoveryClient;

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("STORES");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri().getRawPath();
        }
        return null;
    }
}
