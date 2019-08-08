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
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.node.INode;
import com.hert.core.tool.support.Kv;
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
	public List<MenuVO> buttons(List<Integer> roleId) {
		List<RoleMenu> listRoleMenu = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().in("role_id", roleId).eq("category", MenuTypeEnum.BUTTON.getCategory()));
		List<Integer> listMenuId = listRoleMenu.stream().map(item -> {
			return item.getMenuId();
		}).collect(Collectors.toList());
		List<Menu> listMenu = baseMapper.selectList(new QueryWrapper<Menu>().in("id", listMenuId));
		List<MenuVO> collect = listMenu.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	@Override
	public List<MenuVO> tree(List<Integer> roleId) {
		List<RoleMenu> listRoleMenu = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().in("role_id", roleId).eq("category", MenuTypeEnum.ROUTER.getCategory()));
		List<Integer> listMenuId = listRoleMenu.stream().map(item -> {
			return item.getMenuId();
		}).collect(Collectors.toList());
		List<Menu> listMenu = baseMapper.selectList(new QueryWrapper<Menu>().in("id", listMenuId));
		List<MenuVO> collect = listMenu.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	private MenuVO entityVO(Menu menu) {
		return Func.copy(menu, MenuVO.class);
	}

}
