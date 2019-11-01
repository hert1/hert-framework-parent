package com.hert.base.feign;

import com.hert.base.service.IUserService;
import com.hert.core.tool.api.R;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.feign.IUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("用户服务Feign实现类")
public class UserClient implements IUserClient {

	private IUserService service;

	@Override
	@GetMapping(API_PREFIX + "/user-info-by-id")
	@ApiOperation(value = "通过userId获取用户信息", notes = "")
	public R<UserDTO> userInfo(Integer userId) {
		return R.data(service.userInfo(userId));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info")
	@ApiOperation(value = "通过账号密码获取用户信息", notes = "")
	public R<UserDTO> userInfo(String account, String password) {
		return R.data(service.userInfo(account, password));
	}

}
