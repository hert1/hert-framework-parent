package com.hert.base.wrapper;


import com.hert.base.service.IMenuService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.support.BaseEntityWrapper;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.SpringUtil;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private static IMenuService menuService;


	static {
		menuService = SpringUtil.getBean(IMenuService.class);
	}

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = menuService.getById(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		return menuVO;
	}


	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
