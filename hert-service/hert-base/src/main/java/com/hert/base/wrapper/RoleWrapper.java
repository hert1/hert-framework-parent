package com.hert.base.wrapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hert.base.api.entity.RoleMenu;
import com.hert.base.mapper.RoleMenuMapper;
import com.hert.base.service.IRoleMenuService;
import com.hert.base.service.IRoleService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.support.BaseEntityWrapper;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.SpringUtil;
import com.hert.base.api.entity.Role;
import com.hert.base.api.vo.RoleVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	private static IRoleService roleService;
	private static IRoleMenuService roleMenuService;

	static {
		roleService = SpringUtil.getBean(IRoleService.class);
		roleMenuService = SpringUtil.getBean(IRoleMenuService.class);
	}

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
		if (Func.equals(role.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Role parent = roleService.getById(role.getParentId());
			roleVO.setParentName(parent.getRoleName());
		}
		List<RoleMenu> listRoleMenu = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", role.getId()));
		if(Func.isNotEmpty(listRoleMenu)) {
			roleVO.setPermissions(listRoleMenu.stream().map(item -> item.getMenuId()).collect(Collectors.toList()));
		}
		return roleVO;
	}

	public List<INode> listNodeVO(List<Role> list) {
		List<INode> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
