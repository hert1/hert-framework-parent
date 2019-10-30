package com.hert.core.log.event;


import com.hert.core.log.constant.EventConstant;
import com.hert.core.log.feign.ILogClient;
import com.hert.core.log.model.LogError;
import com.hert.core.log.utils.LogAbstractUtil;
import com.hert.core.tool.props.HertProperties;
import com.hert.core.tool.server.ServerInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 *
 * @author hert
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final HertProperties hertProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(ErrorLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logError, hertProperties, serverInfo);
		logService.saveErrorLog(logError);
	}

}
