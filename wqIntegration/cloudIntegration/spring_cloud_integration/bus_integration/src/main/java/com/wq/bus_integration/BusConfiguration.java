package com.wq.bus_integration;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

/**
 * @PackageName:com.wq.bus_integration
 * @ClassName:BusConfiguration
 * @Description:
 * @author: wq
 * @date 2021/7/29 22:07
 */
@Configuration
@RemoteApplicationEventScan({"com.wq.bus_integration"})
public class BusConfiguration {
}
