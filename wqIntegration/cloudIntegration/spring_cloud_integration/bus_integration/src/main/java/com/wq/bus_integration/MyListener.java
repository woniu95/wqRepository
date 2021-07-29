package com.wq.bus_integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @PackageName:com.wq.bus_integration
 * @ClassName:MyListener
 * @Description:
 * @author: wq
 * @date 2021/7/29 22:53
 */
@Configuration
public class MyListener {


    @Value("${server.port}")
    private String port;

    @EventListener
    public void onCustomRemoteApplicationEvent(MyEvent event) {
        System.out.printf("MyEvent - port : %s , Source : %s , originService : %s , destinationService : %s \n",
                port, event.getSource(), event.getOriginService(), event.getDestinationService());
    }


}
