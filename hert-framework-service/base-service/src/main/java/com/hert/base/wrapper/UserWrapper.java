package com.hert.base.wrapper;


import com.hert.base.api.entity.Dept;
import com.hert.base.api.entity.Role;
import com.hert.base.api.enums.SexEnum;
import com.hert.base.service.IDeptService;
import com.hert.base.service.IRoleService;
import com.hert.base.service.IUserService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.support.BaseEntityWrapper;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.SpringUtil;
import com.hert.base.api.entity.User;
import com.hert.base.api.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IRoleService roleService;
	private static IDeptService deptService;
	private static IUserService userService;

	static {
		roleService = SpringUtil.getBean(IRoleService.class);
		deptService = SpringUtil.getBean(IDeptService.class);
		userService = SpringUtil.getBean(IUserService.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<Role> listRole = roleService.selectRoleByUserId(user.getId());
		List<Dept> listDept = deptService.selectDeptByUserId(user.getId());
		List<String> roleName = listRole.stream().map(item -> {
			return item.getRoleName();
		}).collect(Collectors.toList());
		List<String> deptName = listDept.stream().map(item -> {
			return item.getDeptName();
		}).collect(Collectors.toList());
		List<Integer> roles = listRole.stream().map(item -> {
			return item.getId();
		}).collect(Collectors.toList());
		List<Integer> depts = listDept.stream().map(item -> {
			return item.getId();
		}).collect(Collectors.toList());
		if (Func.equals(user.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			userVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			User parent = userService.getById(user.getParentId());
			userVO.setParentName(parent.getName());
		}
		userVO.setRoleName(roleName);
		userVO.setDeptName(deptName);
		userVO.setRoles(roles);
		userVO.setDepts(depts);
		userVO.setSexName(SexEnum.getName(user.getSex()));
		return userVO;
	}

}
