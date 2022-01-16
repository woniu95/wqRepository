package com.wq.resilence4j.config;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

/**
 * @PackageName:com.wq.resilence4j.config
 * @ClassName:ResilenceService
 * @Description:
 * @author: wq
 * @date 2021/7/27 22:22
 */
public class ResilenceService {

    @CircuitBreaker(name = "BACKEND", fallbackMethod = "fallback")
    @RateLimiter(name = "BACKEND")
    @Bulkhead(name = "BACKEND")
    @Retry(name = "BACKEND", fallbackMethod = "fallback")
    @TimeLimiter(name = "BACKEND")
    public void service(){

    }
}
