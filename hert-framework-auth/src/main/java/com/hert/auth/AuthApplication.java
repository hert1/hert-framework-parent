package com.hert.auth;


import com.hert.core.launch.HertApplication;
import com.hert.core.tool.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户认证服务器
 *
 * @author hert
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class AuthApplication {
	public static void main(String[] args) {
		HertApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
	}

}

