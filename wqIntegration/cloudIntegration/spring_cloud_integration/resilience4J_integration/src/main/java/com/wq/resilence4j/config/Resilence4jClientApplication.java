package com.wq.resilence4j.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @PackageName:com.wq
 * @ClassName:FeignClientApplication
 * @Description:
 * @author: wq
 * @date 2021/7/24 22:26
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class Resilence4jClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilence4jClientApplication.class, args);
    }
}

