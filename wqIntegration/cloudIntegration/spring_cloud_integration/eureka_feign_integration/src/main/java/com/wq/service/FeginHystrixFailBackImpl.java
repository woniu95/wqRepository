package com.wq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @PackageName:com.wq.service
 * @ClassName:FeginFailBackImpl
 * @Description:
 * @author: wq
 * @date 2021/7/25 13:18
 */
@Slf4j
@Component
public class FeginHystrixFailBackImpl implements IEurkaClientFeignService{

    @Override
    public String getInfo() {
        log.debug("请求异常，返回熔断信息");
        return "{请求异常，返回熔断信息}";
    }
}
