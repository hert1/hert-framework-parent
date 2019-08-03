package com.hert.base.feign;

import com.hert.base.service.IUserService;
import com.hert.core.tool.api.R;
import com.hert.base.api.entity.UserInfo;
import com.hert.base.api.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private IUserService service;

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.data(service.userInfo(userId));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info")
	public R<UserInfo> userInfo(String tenantCode, String account, String password) {
		return R.data(service.userInfo(tenantCode, account, password));
	}

}
