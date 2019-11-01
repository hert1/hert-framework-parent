package com.hert.admin;

import com.hert.core.boot.HertApplication;
import com.hert.core.tool.constant.AppConstant;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * admin启动器
 *
 * @author hert
 */
@EnableAdminServer
@SpringCloudApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class AdminApplication {

	public static void main(String[] args) {
		HertApplication.run(AppConstant.APPLICATION_ADMIN_NAME, AdminApplication.class, args);
	}

}
