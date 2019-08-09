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
	 * 树形结构
	 *
	 * @param type
	 * @param roleId
	 * @return
	 */
	List<Menu> tree(Integer type, List<Integer> roleId);


}
