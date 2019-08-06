package com.hert.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.mapper.RoleMapper;
import com.hert.base.service.IRoleMenuService;
import com.hert.base.service.IRoleService;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.CollectionUtil;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Role;
import com.hert.base.api.entity.RoleMenu;
import com.hert.base.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	IRoleMenuService roleMenuService;

	@Override
	public IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role) {
		return page.setRecords(baseMapper.selectRolePage(page, role));
	}

	@Override
	public List<Role> selectRoleByUserId(Integer userId) {
		List<Role> roles = baseMapper.selectRoleByUserId(userId);
		Set<Role> childrenRole = selectChildren(roles);
		childrenRole.addAll(roles);
		roles.clear();
		roles.addAll(childrenRole);
		return roles;
	}

	@Override
	public List<INode> tree(Integer userId) {
		List<Role> ListRole = this.selectRoleByUserId(userId);
		List<INode> collect = ListRole.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	@Override
	public boolean grant(@NotEmpty List<Integer> roleIds, @NotEmpty List<Integer> menuIds) {
		// 删除角色配置的菜单集合
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
		// 组装配置
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenus.add(roleMenu);
		}));
		// 新增配置
		return roleMenuService.saveBatch(roleMenus);
	}

	private RoleVO entityVO(Role role) {
		return Func.copy(role, RoleVO.class);
	}

	private Set<Role> selectChildren(List<Role> listRole) {
		Set<Role> childrenRole = new HashSet<>();
		if (CollectionUtil.isEmpty(listRole)) {
			return childrenRole;
		}
		for (Role role : listRole) {
			List<Role> children = baseMapper.selectList(new QueryWrapper<Role>().eq("parent_id", role.getId()));
			if (CollectionUtil.isNotEmpty(children)) {
				childrenRole.addAll(children);
			}
			Set<Role> nextChildren = selectChildren(children);
			if (CollectionUtil.isNotEmpty(nextChildren)) {
				childrenRole.addAll(nextChildren);
			}
		}
		return childrenRole;
	}

}
