package com.hert.auth.utils;


import com.hert.base.api.entity.User;
import com.hert.base.api.dto.UserDTO;
import com.hert.core.secure.AuthInfo;
import com.hert.core.secure.TokenInfo;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.secure.constant.TokenConstant;
import com.hert.core.tool.utils.Func;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	public final static String TENANT_HEADER_KEY = "Tenant-Code";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

	/**
	 * 创建认证token
	 *
	 * @param userDto 用户信息
	 * @return token
	 */
	public static AuthInfo createAuthInfo(UserDTO userDto) {
		User user = userDto.getUser();

		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.PERMISSIONS, Func.join(userDto.getPermissions())); //添加权限
		param.put(TokenConstant.PERMISSIONS_ID, Func.join(userDto.getPermissionsId())); //添加权限id
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.ACCOUNT_TYPE, Func.toStr(user.getAccountType()));
		param.put(TokenConstant.USER_NAME, user.getAccount());
		param.put(TokenConstant.ROLE_ID, Func.join(userDto.getRoleId())); //添加角色id
		param.put(TokenConstant.ROLE_NAME, Func.join(userDto.getRoleName())); //添加角色

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setAccount(user.getAccount());
		authInfo.setUserName(user.getRealName());
		authInfo.setPermissions(userDto.getPermissions());
		authInfo.setRoles(userDto.getRoleName());
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setLicense(TokenConstant.LICENSE_NAME);

		return authInfo;
	}

	/**
	 * 创建Token
	 *
	 * @param userDto 用户信息
	 * @return refreshToken
	 */
	public static String createToken(UserDTO userDto) {
		return createAuthInfo(userDto).getAccessToken();
}

}
