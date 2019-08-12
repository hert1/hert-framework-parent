package com.hert.base.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.hert.base.api.entity.*;
import com.hert.base.api.entity.UserRole;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.enums.AccountTypeEnum;
import com.hert.base.api.enums.MenuTypeEnum;
import com.hert.base.mapper.*;
import com.hert.base.service.IDeptService;
import com.hert.base.service.IMenuService;
import com.hert.base.service.IRoleService;
import com.hert.base.service.IUserService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.base.BaseServiceImpl;
import com.hert.core.tool.utils.DigestUtil;
import com.hert.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private IRoleService roleService;

    @Autowired
    private IDeptService deptService;

	@Autowired
	private MenuMapper menumapper;

	@Autowired
	private IMenuService menuService;

	@Override
	public boolean submit(User user) {
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		}
		Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getAccount, user.getAccount()));
		if (cnt > 0) {
			throw new ApiException("当前用户已存在!");
		}
		return saveOrUpdate(user);
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user) {
		return null;
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
	@Transactional
	public boolean grant(String userIds, String roleIds) {
		userRoleMapper.delete(new QueryWrapper<UserRole>().in("user_id", Func.toIntList(userIds)));
		for (Integer userId : Func.toIntList(userIds)) {
			for (Integer roleId : Func.toIntList(roleIds)) {
				userRoleMapper.insert(UserRole.builder().userId(userId).roleId(roleId).build());
			}
		}
		return true;
	}

	@Override
	public boolean resetPassword(String userIds) {
		User user = new User();
		user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		user.setUpdateTime(LocalDateTime.now());
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toIntList(userIds)));
	}

	@Override
	public List<String> getRoleName(String roleIds) {
        List<Role> listRole = roleService.list(new QueryWrapper<Role>().lambda().in(Role::getId, roleIds));
        return listRole.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
	}

	@Override
	public List<String> getDeptName(String deptIds) {
        List<Dept> listDept = deptService.list(new QueryWrapper<Dept>().lambda().in(Dept::getId, deptIds));
        return listDept.stream().map(item -> item.getDeptName()).collect(Collectors.toList());
	}

	private UserDTO setRoleAndPermissionInUser (User user) {
		UserDTO userDto = new UserDTO();
		userDto.setUser(user);
		if (user.getAccountType() == AccountTypeEnum.SU_ADMIN.getValue()) {
			List<Role> listRole = roleMapper.selectList(new QueryWrapper<Role>());
			// 添加角色
			if (Func.isNotEmpty(listRole)) {
				userDto.setRoleName(listRole.stream().map(item -> {
					return item.getRoleAlias();
				}).collect(Collectors.toList()));
				userDto.setRoleId(listRole.stream().map(item -> {
					return item.getId();
				}).collect(Collectors.toList()));
				List<Menu> listMenu = menumapper.selectList(new QueryWrapper<Menu>());
				if (Func.isNotEmpty(listMenu)) {
					userDto.setPermissions(listMenu.stream().map(item -> {
						return item.getCode();
					}).collect(Collectors.toList()));
					userDto.setPermissionsId(listMenu.stream().map(item -> {
						return item.getId();
					}).collect(Collectors.toList()));
				}
			}
		} else {
			if (Func.isNotEmpty(user)) {
				// 添加角色
				List<Role> listRole = roleService.selectRoleByUserId(user.getId());
				List<Integer> listRoleId = Arrays.asList();
				if (Func.isNotEmpty(listRole)) {
					listRoleId = listRole.stream().map(item -> {
						return item.getId();
					}).collect(Collectors.toList());
					userDto.setRoleName(listRole.stream().map(item -> {
						return item.getRoleAlias();
					}).collect(Collectors.toList()));
					userDto.setRoleId(listRoleId);
				}
				//添加权限
				List<Menu> listMenu = menuService.list(MenuTypeEnum.ALL.getValue(), listRoleId);
				if (Func.isNotEmpty(listMenu)) {
					userDto.setPermissions(listMenu.stream().map(item -> {
						return item.getCode();
					}).collect(Collectors.toList()));
					userDto.setPermissionsId(listMenu.stream().map(item -> {
						return item.getId();
					}).collect(Collectors.toList()));
				}
			}
		}
		return userDto;
	}

}
