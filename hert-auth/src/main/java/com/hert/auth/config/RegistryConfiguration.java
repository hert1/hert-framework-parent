package com.hert.auth.config;


import com.hert.core.secure.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * secure模块api放行配置
 *
 * @author Chill
 */
@Configuration
public class RegistryConfiguration implements WebMvcConfigurer {

	@Bean
	public SecureRegistry secureRegistry() {
		SecureRegistry secureRegistry = new SecureRegistry();
		secureRegistry.excludePathPatterns("/login/**");
		secureRegistry.excludePathPatterns("/checkLogin/**");
		secureRegistry.excludePathPatterns("/logout/**");
		return secureRegistry;
	}

}
