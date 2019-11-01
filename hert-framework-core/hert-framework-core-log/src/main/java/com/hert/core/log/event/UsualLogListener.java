package com.hert.core.log.event;


import com.hert.core.log.constant.EventConstant;
import com.hert.core.log.feign.ILogClient;
import com.hert.core.log.model.LogUsual;
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
 * 异步监听日志事件
 *
 * @author hert
 */
@Slf4j
@AllArgsConstructor
public class UsualLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final HertProperties hertProperties;

	@Async
	@Order
	@EventListener(UsualLogEvent.class)
	public void saveUsualLog(UsualLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logUsual, hertProperties, serverInfo);
		logService.saveUsualLog(logUsual);
	}

}
