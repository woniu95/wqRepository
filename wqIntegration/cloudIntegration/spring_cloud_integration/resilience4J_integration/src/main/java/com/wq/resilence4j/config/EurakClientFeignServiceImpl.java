package com.wq.resilence4j.config;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @PackageName:com.wq.resilence4j.config
 * @ClassName:EurakClientFeignServiceImpl
 * @Description:
 * @author: wq
 * @date 2021/7/27 22:59
 */
@Service
public class EurakClientFeignServiceImpl{

    @Autowired
    IEurkaClientFeignService eurkaClientFeignService;

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    @RateLimiter(name = "backendA")
    @Bulkhead(name = "backendA")
    @Retry(name = "backendA", fallbackMethod = "fallback")
    @TimeLimiter(name = "backendA")
    public CompletableFuture getInfo() {

        return (CompletableFuture) CompletableFuture.supplyAsync((Supplier<Object>) () -> eurkaClientFeignService.getInfo());
    }

    public CompletableFuture fallback(Throwable e){
        System.out.println("silence4j fallback param1:"+"exception :");
        e.printStackTrace();
        return null;
    }
}
