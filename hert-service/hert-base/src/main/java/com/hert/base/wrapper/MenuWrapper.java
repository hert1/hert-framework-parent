package com.hert.base.wrapper;


import com.hert.base.service.IMenuService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.support.BaseEntityWrapper;
import com.hert.core.tool.api.R;
import com.hert.core.tool.node.ForestNodeMerger;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.SpringUtil;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.feign.IDictClient;
import com.hert.base.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private static IMenuService menuService;

	private static IDictClient dictClient;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
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
		R<String> d1 = dictClient.getValue("menu_category", Func.toInt(menuVO.getCategory()));
		R<String> d2 = dictClient.getValue("button_func", Func.toInt(menuVO.getAction()));
		R<String> d3 = dictClient.getValue("yes_no", Func.toInt(menuVO.getIsOpen()));
		if (d1.isSuccess()) {
			menuVO.setCategoryName(d1.getData());
		}
		if (d2.isSuccess()) {
			menuVO.setActionName(d2.getData());
		}
		if (d3.isSuccess()) {
			menuVO.setIsOpenName(d3.getData());
		}
		return menuVO;
	}


	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
