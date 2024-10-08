package com.app.config;

import com.app.constant.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    private final static Set<String> CONSUMES_PRODUCES = Set.of("application/json");

    @Bean
    public Docket storeAuthApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .consumes(CONSUMES_PRODUCES)
                .produces(CONSUMES_PRODUCES)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(AppConstant.APP_CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }
}