package com.wq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// value 为请求的服务名 即服务的spring.application.name
@FeignClient(value = "EUREKA-CLIENT-INTEGRATION", fallback = FeginHystrixFailBackImpl.class)
public interface IEurkaClientFeignService {


    @RequestMapping(value = "/", method = RequestMethod.POST)
    String getInfo();

}
