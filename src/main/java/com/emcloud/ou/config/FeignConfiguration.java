package com.emcloud.ou.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.emcloud.ou")
public class FeignConfiguration {

}
