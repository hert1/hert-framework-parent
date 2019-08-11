package com.hert.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hert.base.api.enums.MenuTypeEnum;
import com.hert.base.api.vo.RoleVO;
import com.hert.base.mapper.MenuMapper;
import com.hert.base.mapper.RoleMenuMapper;
import com.hert.base.service.IMenuService;
import com.hert.base.service.IRoleMenuService;
import com.hert.base.wrapper.MenuWrapper;
import com.hert.core.secure.LoginUser;
import com.hert.core.secure.utils.SecureUtil;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.support.Kv;
import com.hert.core.tool.utils.CollectionUtil;
import com.hert.core.tool.utils.Func;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.entity.RoleMenu;
import com.hert.base.api.vo.MenuVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	RoleMenuMapper roleMenuMapper;

	@Override
	public List<Menu> list(Integer type, List<Integer> roleId) {
		List<RoleMenu> listRoleMenu = Arrays.asList();
		if(Func.isNotEmpty(roleId)) {
			listRoleMenu = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().in("role_id", roleId));
		}
		List<Integer> listMenuId = listRoleMenu.stream().map(item -> {
			return item.getMenuId();
		}).collect(Collectors.toList());
		List<Menu> listMenu = Arrays.asList();
		if(SecureUtil.getUserRole().contains("administrator")) {
			listMenu = baseMapper.selectList(new QueryWrapper<Menu>().eq(MenuTypeEnum.ALL.getValue() != type, "category", type));
		} else {
			if(Func.isNotEmpty(listMenuId)) {
				listMenu = baseMapper.selectList(new QueryWrapper<Menu>().in("id", listMenuId).eq(MenuTypeEnum.ALL.getValue() != type, "category", type));

			}
		}
		Set<Menu> childrenMenu = selectChildren(type, listMenu);
		childrenMenu.addAll(listMenu);
		listMenu.clear();
		listMenu.addAll(childrenMenu);
		return listMenu;
	}

	private Set<Menu> selectChildren(Integer type, List<Menu> listMenu) {
		Set<Menu> childrenMenu = new HashSet<>();
		if (CollectionUtil.isEmpty(listMenu)) {
			return childrenMenu;
		}
		for (Menu menu : listMenu) {
			List<Menu> children = baseMapper.selectList(new QueryWrapper<Menu>().eq("parent_id", menu.getId()).eq(MenuTypeEnum.ALL.getValue() != type, "category", type));
			if (CollectionUtil.isNotEmpty(children)) {
				childrenMenu.addAll(children);
			}
			Set<Menu> nextChildren = selectChildren(type, children);
			if (CollectionUtil.isNotEmpty(nextChildren)) {
				childrenMenu.addAll(nextChildren);
			}
		}
		return childrenMenu;
	}
}
