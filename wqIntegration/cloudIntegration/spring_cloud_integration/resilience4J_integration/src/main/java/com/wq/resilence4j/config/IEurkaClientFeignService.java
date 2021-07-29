package com.wq.resilence4j.config;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// value 为请求的服务名 即服务的spring.application.name
@FeignClient(value = "EUREKA-CLIENT-INTEGRATION", fallback = FeginHystrixFailBackImpl.class)
public interface IEurkaClientFeignService {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    String getInfo();

    default void fallback(String param1, Throwable e){
        System.out.println("silence4j fallback param1:"+ param1+"exception :");
        e.printStackTrace();
    }
}
