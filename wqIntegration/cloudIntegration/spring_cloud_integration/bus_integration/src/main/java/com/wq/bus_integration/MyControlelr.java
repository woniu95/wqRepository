package com.wq.bus_integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName:com.wq.bus_integration
 * @ClassName:MyControlelr
 * @Description:
 * @author: wq
 * @date 2021/7/29 22:56
 */
@RestController
public class MyControlelr {


    /**
     * Spring Cloud bus 外部化配置
     */
    @Autowired
    private BusProperties busProperties;

    /**
     * 事件发布者
     */
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("/bus/event/publish/custom")
    public boolean publishUserEvent(String msg, @RequestParam(value = "destination", required = false) String destination) {
        //这里由于我没有定义ID ，这里Spring Cloud Bus 自己默认实现了ID
        String instanceId = busProperties.getId();
        Destination destinationObj = () -> destination;
        MyEvent event = new MyEvent(msg, instanceId, destinationObj);
        eventPublisher.publishEvent(event);
        return true;
    }

}
