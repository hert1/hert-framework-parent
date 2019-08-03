package com.hert.core.secure;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体
 *
 * @author Chill
 */
@Data
@Builder
public class HertUser implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@ApiModelProperty(hidden = true)
	private String clientId;

	/**
	 * 用户id
	 */
	@ApiModelProperty(hidden = true)
	private Integer userId;
	/**
	 * 昵称
	 */
	@ApiModelProperty(hidden = true)
	private String userName;
	/**
	 * 账号
	 */
	@ApiModelProperty(hidden = true)
	private String account;
	/**
	 * 角色名
	 */
	@ApiModelProperty(hidden = true)
	private List<String> roleName;

	/**
	 * 权限
	 */
	@ApiModelProperty(hidden = true)
	private List<String> permission;

}
