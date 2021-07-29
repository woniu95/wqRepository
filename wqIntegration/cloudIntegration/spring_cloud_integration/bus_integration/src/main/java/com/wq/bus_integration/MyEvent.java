package com.wq.bus_integration;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @PackageName:com.wq.bus_integration
 * @ClassName:MyEvent
 * @Description:
 * @author: wq
 * @date 2021/7/29 22:09
 */

public class MyEvent extends RemoteApplicationEvent {
    private MyEvent() {
        //一定要有，序列化时会用到
    }

    public MyEvent(Object msg, String originService, Destination destination) {
        super(msg, originService, destination);
    }

}
