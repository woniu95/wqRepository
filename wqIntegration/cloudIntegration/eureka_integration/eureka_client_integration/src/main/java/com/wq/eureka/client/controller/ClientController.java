package com.wq.eureka.client.controller;

import com.wq.eureka.client.context.DiscoveryClientContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @PackageName:com.wq.eureka.client.controller
 * @ClassName:ClientController
 * @Description:
 * @author: wq
 * @date 2021/7/24 14:36
 */
@RestController
public class ClientController {
    @Autowired
    DiscoveryClientContext discoveryClientContext;

    @RequestMapping("/")
    public String home() {
        return "eureka client";
    }

    @RequestMapping("/requestOtherClient")
    public URI requestOtherClient() {
        URI serviceUrl = discoveryClientContext.serviceUrl();
        return serviceUrl;
    }
}
