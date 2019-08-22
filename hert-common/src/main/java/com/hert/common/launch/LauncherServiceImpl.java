package com.hert.common.launch;

import com.hert.common.constant.CommonConstant;
import com.hert.core.launch.constant.NacosConstant;
import com.hert.core.launch.constant.SentinelConstant;
import com.hert.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author hert
 */
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		props.setProperty("spring.cloud.nacos.discovery.server-addr", CommonConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.nacos.config.server-addr", CommonConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.sentinel.transport.dashboard", CommonConstant.sentinelAddr(profile));

		// 从nacos中加载规到sentinel
		props.setProperty("spring.cloud.sentinel.datasource.ds.nacos.server-addr",  CommonConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.sentinel.datasource.ds.nacos.dataId", appName);
		props.setProperty("spring.cloud.sentinel.datasource.ds.nacos.groupId", CommonConstant.NACOS_SENTINEL_FLOW_GROUP);
		//props.setProperty("spring.cloud.sentinel.datasource.ds.nacos.rule-type", "degrade");
	}

}
