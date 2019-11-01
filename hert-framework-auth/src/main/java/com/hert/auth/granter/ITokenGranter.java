package com.hert.auth.granter;


import com.hert.base.api.dto.UserDTO;

/**
 * 授权认证统一接口.
 *
 * @author Chill
 */
public interface ITokenGranter {

	/**
	 * 获取用户信息
	 *
	 * @param tokenParameter 授权参数
	 * @return UserInfo
	 */
	UserDTO grant(TokenParameter tokenParameter);

}
