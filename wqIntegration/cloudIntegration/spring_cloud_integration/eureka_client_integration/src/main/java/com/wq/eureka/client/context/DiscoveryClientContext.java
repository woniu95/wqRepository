package com.wq.eureka.client.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URI;
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

    public URI serviceUrl() {
        List<String> services = discoveryClient.getServices();
        List<ServiceInstance> list = discoveryClient.getInstances(services.get(0));
        if (list != null && list.size() > 0) {
            return list.get(0).getUri();
        }
        return null;
    }
}
