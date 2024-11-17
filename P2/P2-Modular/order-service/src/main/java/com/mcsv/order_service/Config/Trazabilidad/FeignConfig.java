package com.mcsv.order_service.Config.Trazabilidad;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    private final TraceFeignClientInterceptor traceFeignClientInterceptor;

    public FeignConfig(TraceFeignClientInterceptor traceFeignClientInterceptor) {
        this.traceFeignClientInterceptor = traceFeignClientInterceptor;
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return traceFeignClientInterceptor;
    }
}
