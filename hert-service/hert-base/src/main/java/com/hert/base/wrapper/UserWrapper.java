package com.hert.base.wrapper;


import com.hert.base.api.enums.SexEnum;
import com.hert.base.service.IDeptService;
import com.hert.base.service.IRoleService;
import com.hert.core.mp.support.BaseEntityWrapper;
import com.hert.core.tool.utils.BeanUtil;
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

	static {
		roleService = SpringUtil.getBean(IRoleService.class);
		deptService = SpringUtil.getBean(IDeptService.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = roleService.selectRoleByUserId(user.getId()).stream().map(item -> {
			return item.getRoleName();
		}).collect(Collectors.toList());
		List<String> deptName = deptService.selectDeptByUserId(user.getId()).stream().map(item -> {
			return item.getDeptName();
		}).collect(Collectors.toList());
		userVO.setRoleName(roleName);
		userVO.setDeptName(deptName);
		userVO.setSexName(SexEnum.getName(user.getSex()));
		return userVO;
	}

}
