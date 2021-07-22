package com.wq.eureka.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @PackageName:com.wq.eureka.server
 * @ClassName:EurekaServerApplication
 * @Description:
 * @author: wq
 * @date 2021/7/22 21:47
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
