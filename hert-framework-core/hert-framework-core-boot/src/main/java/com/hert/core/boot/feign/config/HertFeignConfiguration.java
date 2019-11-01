package com.hert.core.boot.feign.config;

import com.hert.core.boot.feign.FeignHystrixConcurrencyStrategy;
import com.hert.core.boot.feign.HertFeignRequestHeaderInterceptor;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB配置
 *
 * @author hert
 */
@Slf4j
@Configuration
@EnableCaching
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HertFeignConfiguration implements WebMvcConfigurer {

	@Bean
	@ConditionalOnMissingBean
	public RequestInterceptor requestInterceptor() {
		return new HertFeignRequestHeaderInterceptor();
	}

	@Bean
	public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
		return new FeignHystrixConcurrencyStrategy();
	}

}
