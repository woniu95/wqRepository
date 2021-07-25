package com.wq.eureka.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @PackageName:com.wq
 * @ClassName:TestApplication
 * @Description:
 * @author: wq
 * @date 2021/7/21 23:23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(EurekaClientApplication.class).web(WebApplicationType.SERVLET).initializers((ConfigurableApplicationContext context) -> {
            System.setProperty("logDir", context.getEnvironment().getProperty("spring.application.name"));
        }).run(args);
    }

}
