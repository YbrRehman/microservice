package com.cdac.product_service.dbconnect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
    @Bean
    public DriverConnect driverConnect() {
        return new DriverConnect();
    }
}
