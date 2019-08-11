package com.hert.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.api.form.edit.RoleForm;
import com.hert.base.mapper.RoleMapper;
import com.hert.base.service.IRoleMenuService;
import com.hert.base.service.IRoleService;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.utils.CollectionUtil;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Role;
import com.hert.base.api.entity.RoleMenu;
import com.hert.base.api.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author hert
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	@Autowired
	IRoleMenuService roleMenuService;


	@Override
	public List<Role> selectRoleByUserId(Integer userId) {
		List<Role> roles = Arrays.asList();
		if(SecureUtil.getUserRole().contains("administrator")) {
			roles = baseMapper.selectList(new QueryWrapper<Role>());
		} else {
			roles = baseMapper.selectRoleByUserId(userId);
		}
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
	@Transactional
	public Boolean saveOrUpdate(RoleForm form) {
		Role role = Func.copy(form, Role.class);
		boolean saveRole = this.saveOrUpdate(role);
		List<Integer> permissions = form.getPermissions();
		roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", role.getId()));
		boolean saveRoleMenu = roleMenuService.saveBatch(permissions.stream().map(item -> RoleMenu.builder().menuId(item).roleId(role.getId()).build()).collect(Collectors.toList()));
		return saveRole && saveRoleMenu;
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
