package com.hert.base.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.entity.Dept;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.entity.Role;
import com.hert.base.api.entity.User;
import com.hert.base.api.entity.UserDept;
import com.hert.base.api.entity.UserRole;
import com.hert.base.api.enums.AccountTypeEnum;
import com.hert.base.api.enums.MenuTypeEnum;
import com.hert.base.api.form.edit.UserForm;
import com.hert.base.mapper.MenuMapper;
import com.hert.base.mapper.RoleMapper;
import com.hert.base.mapper.UserMapper;
import com.hert.base.service.IDeptService;
import com.hert.base.service.IMenuService;
import com.hert.base.service.IRoleService;
import com.hert.base.service.IUserDeptService;
import com.hert.base.service.IUserRoleService;
import com.hert.base.service.IUserService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.base.BaseServiceImpl;
import com.hert.core.tool.utils.DigestUtil;
import com.hert.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private IUserRoleService userRoleService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IDeptService deptService;

	@Autowired
    private IUserDeptService userDeptService;

	@Override
    @Transactional
	public boolean submit(UserForm form) {
        User user = Func.copy(form, User.class);
        Boolean userSubmit = true;
        if(null == user.getId()) {
			Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getAccount, form.getAccount()));
			if (cnt > 0) {
				throw new ApiException("当前用户已存在!");
			}
			user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
			userSubmit = this.save(user);
        } else {
			userSubmit = this.updateById(user);
        }
        List<Integer> depts = form.getDepts();
        List<Integer> roles = form.getRoles();
		Boolean userDeptSubmit = true;
		Boolean userRoleSubmit = true;
        if(Func.isNotEmpty(depts)) {
			userDeptService.remove(new QueryWrapper<UserDept>().lambda().eq(UserDept::getUserId, user.getId()));
        	userDeptSubmit = userDeptService.saveBatch(depts.stream().map(item -> UserDept.builder().deptId(item).userId(user.getId()).build()).collect(Collectors.toList()));
		}
		if(Func.isNotEmpty(roles)) {
			userRoleService.remove(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, user.getId()));
			userRoleSubmit = userRoleService.saveBatch(roles.stream().map(item -> UserRole.builder().roleId(item).userId(user.getId()).build()).collect(Collectors.toList()));
		}
        return userSubmit && userDeptSubmit && userRoleSubmit;
	}

	@Override
	public UserDTO userInfo(Integer userId) {
		User user = baseMapper.selectById(userId);
		return setRoleAndPermissionInUser(user);
	}

	@Override
	public UserDTO userInfo(String account, String password) {
		User user = baseMapper.selectOne(new QueryWrapper<User>().eq("account", account).eq("password", password));
		return setRoleAndPermissionInUser(user);
	}

	@Override
	public boolean resetPassword(List<Integer> userIds) {
		if(Func.isNotEmpty(userIds)) {
			String new_password = DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD);
			List<User> userList = userIds.stream().map(item -> {
				return User.builder().id(item).password(new_password).build();
			}).collect(Collectors.toList());
			return this.updateBatchById(userList);
		}
		return false;
	}

	/*
	* 对用户添加角色以及权限
	* */
	private UserDTO setRoleAndPermissionInUser (User user) {
		UserDTO userDto = new UserDTO();
		userDto.setUser(user);
        List<Role> listRole = Arrays.asList();
        List<Menu> listMenu = Arrays.asList();
        if (user.getAccountType() == AccountTypeEnum.SU_ADMIN.getValue()) {
			 listRole = roleService.list();
             listMenu = menuService.list();
        } else {
			if (Func.isNotEmpty(user)) {
				listRole = roleService.selectRoleByUserId(user.getId());
				List<Integer> listRoleId = Arrays.asList();
				if (Func.isNotEmpty(listRole)) {
					listRoleId = listRole.stream().map(item -> {
						return item.getId();
					}).collect(Collectors.toList());
				}
				listMenu = menuService.list(MenuTypeEnum.ALL.getValue(), listRoleId);
			}
		}
        // 添加角色
        if (Func.isNotEmpty(listRole)) {
            userDto.setRoleName(listRole.stream().map(item -> {
                return item.getRoleAlias();
            }).collect(Collectors.toList()));
            userDto.setRoleId(listRole.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList()));
        }
        // 添加权限
        if (Func.isNotEmpty(listMenu)) {
            userDto.setPermissions(listMenu.stream().map(item -> {
                return item.getCode();
            }).collect(Collectors.toList()));
            userDto.setPermissionsId(listMenu.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList()));
        }
		return userDto;
	}

}
