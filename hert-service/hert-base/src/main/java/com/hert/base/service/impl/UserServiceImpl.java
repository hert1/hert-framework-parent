package com.hert.base.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.hert.base.api.entity.*;
import com.hert.base.api.entity.UserRole;
import com.hert.base.api.dto.UserDTO;
import com.hert.base.mapper.*;
import com.hert.base.service.IUserService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.base.BaseServiceImpl;
import com.hert.core.tool.utils.DigestUtil;
import com.hert.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
	private RoleMenuMapper roleMenuMapper;

	@Autowired
	private MenuMapper menumapper;

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
		return page.setRecords(baseMapper.selectUserPage(page, user));
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
		return baseMapper.getRoleName(Func.toStrArray(roleIds));
	}

	@Override
	public List<String> getDeptName(String deptIds) {
		return baseMapper.getDeptName(Func.toStrArray(deptIds));
	}

	private UserDTO setRoleAndPermissionInUser (User user) {
		UserDTO userDto = new UserDTO();
		userDto.setUser(user);
		if (Func.isNotEmpty(user)) {
			List<UserRole> listUserRole = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
			List<Integer> listRoleId = listUserRole.stream().map(item -> {
				return item.getRoleId();
			}).collect(Collectors.toList());
			List<Role> listRole = roleMapper.selectList(new QueryWrapper<Role>().in(Func.isNotEmpty(listRoleId),"id", listRoleId.toArray()));
			// 添加角色
			if (Func.isNotEmpty(listRole)) {
				userDto.setRoleName(listRole.stream().map(item -> {
					return item.getRoleAlias();
				}).collect(Collectors.toList()));
				userDto.setRoleId(listRole.stream().map(item -> {
					return item.getId();
				}).collect(Collectors.toList()));
			}
			List<RoleMenu> listRoleMenu = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().in("role_id", listRoleId));
			List<Menu> listMenu = null;
			if (Func.isNotEmpty(listRoleMenu)) {
				List<Integer> listMenuId = listRoleMenu.stream().map(item -> {
					return item.getMenuId();
				}).collect(Collectors.toList());
				listMenu = menumapper.selectList(new QueryWrapper<Menu>().in("id", listMenuId));
			}
			//添加权限
			if (Func.isNotEmpty(listMenu)) {
				userDto.setPermissions(listMenu.stream().map(item -> {
					return item.getCode();
				}).collect(Collectors.toList()));
				userDto.setPermissionsId(listMenu.stream().map(item -> {
					return item.getId();
				}).collect(Collectors.toList()));
			}
		}
		return userDto;
	}

}
