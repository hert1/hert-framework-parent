package com.hert.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.mapper.RoleMapper;
import com.hert.base.service.IRoleMenuService;
import com.hert.base.service.IRoleService;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.constant.RoleConstant;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.CollectionUtil;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Role;
import com.hert.base.api.entity.RoleMenu;
import com.hert.base.api.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
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
		return baseMapper.selectRoleByUserId(userId);
	}

	@Override
	public List<RoleVO> tree(Integer userId) {
	/*	String userRole = SecureUtil.getUserRole();
		String excludeRole = null;
		if (!CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMIN)) {
			excludeRole = RoleConstant.ADMIN;
		}*/
		List<Role> ListRole = this.selectRoleByUserId(userId);
	//	List<INode> collect = ListRole.stream().map(this::entityVO).collect(Collectors.toList());
	//	return ForestNodeMerger.merge(collect);
		return null;
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


}
