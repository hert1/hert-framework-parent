package com.hert.base;

import com.hert.core.launch.HertApplication;
import com.hert.core.launch.constant.AppConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desk启动器
 *
 * @author Chill
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class BaseApplication {

	public static void main(String[] args) {
		HertApplication.run(AppConstant.APPLICATION_BASE_NAME, BaseApplication.class, args);
	}

}

