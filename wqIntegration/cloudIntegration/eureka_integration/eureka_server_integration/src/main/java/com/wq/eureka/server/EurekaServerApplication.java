package com.wq.eureka.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @PackageName:com.wq.eureka.server
 * @ClassName:EurekaServerApplication
 * @Description:
 * @author: wq
 * @date 2021/7/22 21:47
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class EurekaServerApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerApplication.class).web(WebApplicationType.SERVLET)
                .initializers((ConfigurableApplicationContext context) -> {
                    System.setProperty("logDir", context.getEnvironment().getProperty("spring.application.name"));
                }).run(args);
    }

}
