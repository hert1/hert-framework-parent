package com.hert.core.log.config;

import com.hert.core.log.aspect.ApiLogAspect;
import com.hert.core.log.event.ApiLogListener;
import com.hert.core.log.event.ErrorLogListener;
import com.hert.core.log.event.UsualLogListener;
import com.hert.core.log.feign.ILogClient;
import com.hert.core.log.logger.HertLogger;
import com.hert.core.tool.props.HertProperties;
import com.hert.core.tool.server.ServerInfo;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class HertLogToolAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final HertProperties hertProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public HertLogger hertLogger() {
		return new HertLogger();
	}

	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, hertProperties);
	}

	@Bean
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, hertProperties);
	}

	@Bean
	public UsualLogListener hertEventListener() {
		return new UsualLogListener(logService, serverInfo, hertProperties);
	}

}
