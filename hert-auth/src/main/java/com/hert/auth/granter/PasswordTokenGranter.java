package com.hert.auth.granter;

import com.hert.auth.enums.HertUserEnum;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.feign.IUserClient;
import com.hert.core.tool.api.R;
import com.hert.core.tool.utils.DigestUtil;
import com.hert.core.tool.utils.Func;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserClient userClient;

	@Override
	public UserDTO grant(TokenParameter tokenParameter) {
		String tenantCode = tokenParameter.getArgs().getStr("tenantCode");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");
		UserDTO userDto = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			R<UserDTO> result;
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(HertUserEnum.WEB.getName())) {
				result = userClient.userInfo(account, DigestUtil.encrypt(password));
			} else if (userType.equals(HertUserEnum.APP.getName())) {
				result = userClient.userInfo(account, DigestUtil.encrypt(password));
			} else {
				result = userClient.userInfo(account, DigestUtil.encrypt(password));
			}
			userDto = result.isSuccess() ? result.getData() : null;
		}
		return userDto;
	}

}
