package com.hert.core.log.feign;

import com.hert.core.log.model.LogApi;
import com.hert.core.log.model.LogError;
import com.hert.core.log.model.LogUsual;
import com.hert.core.tool.api.R;
import com.hert.core.tool.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_BASE_NAME,
	fallback = LogClientFallback.class
)
public interface ILogClient {

	String API_PREFIX = "/log";

	/**
	 * 保存错误日志
	 *
	 * @param log 日志实体
	 * @return boolean
	 */
	@PostMapping(API_PREFIX + "/saveUsualLog")
	R<Boolean> saveUsualLog(@RequestBody LogUsual log);

	/**
	 * 保存操作日志
	 *
	 * @param log 日志实体
	 * @return boolean
	 */
	@PostMapping(API_PREFIX + "/saveApiLog")
	R<Boolean> saveApiLog(@RequestBody LogApi log);

	/**
	 * 保存错误日志
	 *
	 * @param log 日志实体
	 * @return boolean
	 */
	@PostMapping(API_PREFIX + "/saveErrorLog")
	R<Boolean> saveErrorLog(@RequestBody LogError log);

}
