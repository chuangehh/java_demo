package com.chuanchuan.recharge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class DtxCmRechargeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtxCmRechargeApplication.class, args);
    }

}
