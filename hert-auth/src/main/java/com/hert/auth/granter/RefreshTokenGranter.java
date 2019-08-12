package com.hert.auth.granter;

import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.feign.IUserClient;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.secure.constant.TokenConstant;
import com.hert.core.tool.api.R;
import com.hert.core.tool.utils.Func;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * RefreshTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	private IUserClient userClient;

	@Override
	public UserDTO grant(TokenParameter tokenParameter) {
		String grantType = tokenParameter.getArgs().getStr("grantType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
		UserDTO userDto = null;
		if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
			Claims claims = SecureUtil.getClaims(refreshToken);
			R<UserDTO> result = userClient.userInfo(Func.toInt(claims.get(TokenConstant.USER_ID)));
			userDto = result.isSuccess() ? result.getData() : null;
		}
		return userDto;
	}
}
