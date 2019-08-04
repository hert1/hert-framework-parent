package com.hert.base.api.feign;

import com.hert.base.api.entity.UserInfo;
import com.hert.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class IUserClientFallback implements IUserClient {

	@Override
	public R<UserInfo> userInfo(Integer userId) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userInfo(String tenantCode, String account, String password) {
		return R.fail("未获取到账号信息");
	}
}
