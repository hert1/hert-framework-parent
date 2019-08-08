package com.hert.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hert.core.secure.LoginUser;
import com.hert.core.tool.support.Kv;
import com.hert.base.api.entity.Menu;
import com.hert.base.api.vo.MenuVO;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> buttons(List<Integer> roleId);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree(List<Integer> roleId);


}
