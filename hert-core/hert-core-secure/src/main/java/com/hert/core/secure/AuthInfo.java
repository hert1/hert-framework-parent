package com.hert.core.secure;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * AuthInfo
 *
 * @author Chill
 */
@Data
@ApiModel(description = "认证信息")
public class AuthInfo implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id ;
	@ApiModelProperty(value = "令牌")
	private String accessToken;
	@ApiModelProperty(value = "令牌类型")
	private String tokenType;
	@ApiModelProperty(value = "刷新令牌")
	private String refreshToken;
	@ApiModelProperty(value = "头像")
	private String avatar = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";
	@ApiModelProperty(value = "权限")
	private List<String> permissions;
	@ApiModelProperty(value = "角色")
	private List<String> roles;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "账号名")
	private String account;
	@ApiModelProperty(value = "过期时间")
	private long expiresIn;
	@ApiModelProperty(value = "许可证")
	private String license = "powered by hert";
}
