package com.hert.base.api.vo;

import com.hert.base.api.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserVO对象", description = "UserVO对象")
public class UserVO extends User {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 角色名
	 */
	private List<String> roleName;

	/**
	 * 部门名
	 */
	private List<String> deptName;
	/**
	 * 角色名
	 */
	private List<Integer> roles;

	/**
	 * 部门名
	 */
	private List<Integer> depts;

	/**
	 * 性别
	 */
	private String sexName;

	/**
	 * 上级name
	 */
	private String parentName;
}
