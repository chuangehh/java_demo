package com.chuanchuan.recharge.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"com.chuanchuan.recharge.notify.api"})
public class DtxJdReceiveNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtxJdReceiveNotifyApplication.class, args);
    }

}
