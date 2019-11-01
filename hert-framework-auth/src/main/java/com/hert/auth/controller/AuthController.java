package com.hert.auth.controller;

import com.hert.auth.form.LoginForm;
import com.hert.auth.granter.ITokenGranter;
import com.hert.auth.granter.TokenGranterBuilder;
import com.hert.auth.granter.TokenParameter;
import com.hert.auth.utils.TokenUtil;
import com.hert.base.api.dto.UserDTO;
import com.hert.core.secure.AuthInfo;
import com.hert.core.secure.constant.TokenConstant;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.api.R;
import com.hert.core.tool.api.ResultCode;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.RandomType;
import com.hert.core.tool.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 认证模块
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "授权接口")
public class AuthController {

	@Autowired
	private RedisTemplate redisTemplate;

	@PostMapping("checkLogin")
	@ApiOperation(value = "检查登录", notes = "")
	public R<AuthInfo> checkLogin(@Valid @RequestBody LoginForm form) {
		AuthInfo authInfo = null;
		if (Func.equals(form.getGrantType(), TokenConstant.CHECK_LOGIN)) {
			authInfo = (AuthInfo) redisTemplate.opsForValue().get(form.getCode());
		}
		if (Func.isEmpty(authInfo)) {
			return R.fail(ResultCode.RE_LOGIN);
		}

		return R.data(authInfo);
	}

	@PostMapping("refreshToken")
	@ApiOperation(value = "刷新token", notes = "")
	public R<AuthInfo> refreshToken(@Valid @RequestBody LoginForm form) {
		AuthInfo authInfo = null;
		TokenParameter tokenParameter = new TokenParameter();
		if (Func.equals(form.getGrantType(), TokenConstant.REFRESH_TOKEN)) {
			tokenParameter.getArgs().set("grantType", form.getGrantType()).set("refreshToken", form.getRefreshToken());
			ITokenGranter granter = TokenGranterBuilder.getGranter(form.getGrantType());
			UserDTO userDto = granter.grant(tokenParameter);
			if (userDto == null || userDto.getUser() == null || userDto.getUser().getId() == null) {
				return R.fail(TokenUtil.USER_NOT_FOUND);
			}
			if (userDto.getUser().getStatus() == 0) {
				return R.fail(TokenUtil.USER_STOP_USE);
			}
			authInfo = TokenUtil.createAuthInfo(userDto);
		}
		return R.data(authInfo);
	}

	@PostMapping("logout")
	@ApiOperation(value = "退出" )
	public R<String> logout() {
		return R.data("success");
	}

	@PostMapping("login")
	@ApiOperation(value = "登陆", notes = "账号:account,密码:password")
	public R<String> login(@Valid @RequestBody LoginForm form) {

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("account", form.getAccount()).set("password", form.getPassword()).set("grantType", form.getGrantType()).set("refreshToken",
				form.getRefreshToken()).set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(form.getGrantType());
		UserDTO userDto = granter.grant(tokenParameter);

		if (userDto == null || userDto.getUser() == null || userDto.getUser().getId() == null) {
			return R.fail(TokenUtil.USER_NOT_FOUND);
		}
		if (userDto.getUser().getStatus() == 0) {
			return R.fail(TokenUtil.USER_STOP_USE);
		}
		AuthInfo authInfo = TokenUtil.createAuthInfo(userDto);
		String refreshToken = TokenUtil.createRefreshToken(userDto);
		authInfo.setRefreshToken(refreshToken);
		String code = Func.random(6, RandomType.STRING);
		redisTemplate.opsForValue().set(code, authInfo, 30*60*1000);
		return R.data(code);
	}

}
