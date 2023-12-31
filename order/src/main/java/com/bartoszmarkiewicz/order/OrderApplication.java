package com.bartoszmarkiewicz.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.bartoszmarkiewicz.order",
                "com.bartoszmarkiewicz.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.bartoszmarkiewicz.clients"
)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
