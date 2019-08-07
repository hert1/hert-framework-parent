package com.hert.base.api.dto;

import com.hert.base.api.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据传输对象实体类
 * 用户信息
 *
 * @author Chill
 */
@Data
@ApiModel(description = "用户信息")
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户基础信息
	 */
	@ApiModelProperty(value = "用户")
	private User user;

	/**
	 * 权限标识name集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<String> permissions;

	/**
	 * 角色name集合
	 */
	@ApiModelProperty(value = "角色集合")
	private List<String> roleName;

	/**
	 * 权限标识id集合
	 */
	@ApiModelProperty(value = "权限id集合")
	private List<Integer> permissionsId;

	/**
	 * 角色id集合
	 */
	@ApiModelProperty(value = "角色id集合")
	private List<Integer> roleId;

}
