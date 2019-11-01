package com.hert.core.secure.config;


import com.hert.core.secure.aspect.AuthAspect;
import com.hert.core.secure.interceptor.ClientInterceptor;
import com.hert.core.secure.interceptor.SecureInterceptor;
import com.hert.core.secure.props.HertClientProperties;
import com.hert.core.secure.props.HertSecureProperties;
import com.hert.core.secure.provider.ClientDetailsServiceImpl;
import com.hert.core.secure.provider.IClientDetailsService;
import com.hert.core.secure.registry.SecureRegistry;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author Chill
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({HertSecureProperties.class, HertClientProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final HertSecureProperties secureProperties;

	private final HertClientProperties clientProperties;

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		clientProperties.getClient().forEach(cs -> registry.addInterceptor(new ClientInterceptor(cs.getClientId())).addPathPatterns(cs.getPathPatterns()));

		if (secureRegistry.isEnable()) {
			registry.addInterceptor(new SecureInterceptor())
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getExcludePatterns());
		}
	}

	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

	@Bean
	@ConditionalOnMissingBean(IClientDetailsService.class)
	public IClientDetailsService clientDetailsService() {
		return new ClientDetailsServiceImpl(jdbcTemplate);
	}

}
