package com.hert.auth.controller;

import com.hert.auth.granter.ITokenGranter;
import com.hert.auth.granter.TokenGranterBuilder;
import com.hert.auth.granter.TokenParameter;
import com.hert.auth.utils.TokenUtil;
import com.hert.base.api.dto.UserDTO;
import com.hert.core.secure.AuthInfo;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.api.R;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证模块
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	@PostMapping("checkLogin")
	@ApiOperation(value = "检查登录", notes = "")
	public R<AuthInfo> token(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "refresh_token", required = false) String grantType) {
		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("grantType", grantType).set("refreshToken", SecureUtil.getHeader());
		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserDTO userDto = granter.grant(tokenParameter);
		return R.data(TokenUtil.createAuthInfo(userDto));
	}

	@PostMapping("logout")
	@ApiOperation(value = "退出" )
	public R<String> logout() {
		return R.data("success");
	}

	@PostMapping("login")
	@ApiOperation(value = "登陆", notes = "账号:account,密码:password")
	public R<String> login(@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = "password", required = false) String grantType,
							  @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
							  @ApiParam(value = "租户编号", required = true) @RequestParam(defaultValue = "000000", required = false) String tenantCode,
							  @ApiParam(value = "账号") @RequestParam(required = false) String account,
							  @ApiParam(value = "密码") @RequestParam(required = false) String password) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantCode", tenantCode).set("account", account).set("password", password).set("grantType", grantType).set("refreshToken", refreshToken).set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserDTO userDto = granter.grant(tokenParameter);

		if (userDto == null || userDto.getUser() == null || userDto.getUser().getId() == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}

		return R.data(TokenUtil.createToken(userDto));
	}

}
