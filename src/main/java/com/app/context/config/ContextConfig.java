package com.app.context.config;

import com.app.context.constant.ContextConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class ContextConfig {
    @Bean(ContextConstant.APP_CONFIG_MAP)
    public ConcurrentMap<String, String> getAppConfigMap() {
        return new ConcurrentHashMap<>();
    }
}
