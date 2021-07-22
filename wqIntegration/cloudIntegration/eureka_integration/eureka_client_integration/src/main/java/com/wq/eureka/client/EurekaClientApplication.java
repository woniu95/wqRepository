package com.wq.eureka.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @PackageName:com.wq
 * @ClassName:TestApplication
 * @Description:
 * @author: wq
 * @date 2021/7/21 23:23
 */
@SpringBootApplication
@RestController
public class EurekaClientApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {

        new SpringApplicationBuilder(EurekaClientApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
