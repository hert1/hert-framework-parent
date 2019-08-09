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
	public List<Menu> tree(Integer type, List<Integer> roleId) {
		List<RoleMenu> listRoleMenu = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().in("role_id", roleId));
		List<Integer> listMenuId = listRoleMenu.stream().map(item -> {
			return item.getMenuId();
		}).collect(Collectors.toList());
		List<Menu> listMenu = null;
		if (MenuTypeEnum.ALL.getValue() == type) {
			listMenu = baseMapper.selectList(new QueryWrapper<Menu>().in("id", listMenuId));
		} else {
			listMenu = baseMapper.selectList(new QueryWrapper<Menu>().in("id", listMenuId).eq("category", type));
		}
		return listMenu;
	}


}
