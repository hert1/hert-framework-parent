package com.hert.base.api.feign;


import com.hert.base.api.dto.UserDTO;
import com.hert.core.tool.api.R;
import com.hert.core.tool.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_BASE_NAME,
	fallback = IUserClientFallback.class
)
public interface IUserClient {

	String API_PREFIX = "/user";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info-by-id")
	R<UserDTO> userInfo(@RequestParam("userId") Integer userId);

	/**
	 * 获取用户信息
	 *
	 * @param account    账号
	 * @param password   密码
	 * @return
	 */
	@GetMapping(API_PREFIX + "/user-info")
	R<UserDTO> userInfo(@RequestParam("account") String account, @RequestParam("password") String password);

}
