package com.hert.core.secure.auth;


import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.constant.RoleConstant;
import com.hert.core.tool.utils.CollectionUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.StringUtil;

import java.util.List;

/**
 * 权限判断
 *
 * @author Chill
 */
public class AuthFun {

	/**
	 * 放行所有请求
	 *
	 * @return {boolean}
	 */
	public boolean permitAll() {
		return true;
	}

	/**
	 * 只有超管角色才可访问
	 *
	 * @return {boolean}
	 */
	public boolean denyAll() {
		return hasRole(RoleConstant.ADMIN);
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 单角色
	 * @return {boolean}
	 */
	public boolean hasRole(String role) {
		return hasAnyRole(role);
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 角色集合
	 * @return {boolean}
	 */
	public boolean hasAnyRole(String... role) {
		List<String> listUserRole = SecureUtil.getUser().getRoleName();
		if (CollectionUtil.isEmpty(listUserRole)) {
			return false;
		}
		String[] roles = (String[]) listUserRole.toArray();
		for (String r : role) {
			if (CollectionUtil.contains(roles, r)) {
				return true;
			}
		}
		return false;
	}

}
