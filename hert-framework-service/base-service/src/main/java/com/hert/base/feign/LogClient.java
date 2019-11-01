package com.hert.base.feign;

import com.hert.base.service.ILogApiService;
import com.hert.base.service.ILogErrorService;
import com.hert.base.service.ILogUsualService;
import com.hert.core.log.feign.ILogClient;
import com.hert.core.log.model.LogApi;
import com.hert.core.log.model.LogError;
import com.hert.core.log.model.LogUsual;
import com.hert.core.tool.api.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志服务Feign实现类
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
public class LogClient implements ILogClient {

	ILogUsualService usualLogService;

	ILogApiService apiLogService;

	ILogErrorService errorLogService;

	@Override
	@PostMapping(ILogClient.API_PREFIX + "/saveUsualLog")
	public R<Boolean> saveUsualLog(@RequestBody LogUsual log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(usualLogService.save(log));
	}

	@Override
	@PostMapping(ILogClient.API_PREFIX + "/saveApiLog")
	public R<Boolean> saveApiLog(@RequestBody LogApi log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(apiLogService.save(log));
	}

	@Override
	@PostMapping(ILogClient.API_PREFIX + "/saveErrorLog")
	public R<Boolean> saveErrorLog(@RequestBody LogError log) {
		log.setParams(log.getParams().replace("&amp;", "&"));
		return R.data(errorLogService.save(log));
	}
}
