package com.hert.base.wrapper;


import com.hert.base.api.entity.Client;
import com.hert.base.api.entity.Dept;
import com.hert.base.api.entity.Role;
import com.hert.base.api.entity.User;
import com.hert.base.api.enums.SexEnum;
import com.hert.base.api.vo.ClientVO;
import com.hert.base.api.vo.UserVO;
import com.hert.base.service.IDeptService;
import com.hert.base.service.IRoleService;
import com.hert.base.service.IUserService;
import com.hert.common.constant.CommonConstant;
import com.hert.core.mp.support.BaseEntityWrapper;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.SpringUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class ClientWrapper extends BaseEntityWrapper<Client, ClientVO> {

	private static IUserService userService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
	}

	public static ClientWrapper build() {
		return new ClientWrapper();
	}


	@Override
	public ClientVO entityVO(Client entity) {
		ClientVO clientVO = BeanUtil.copy(entity, ClientVO.class);
		User createUser = userService.getById(clientVO.getCreateUser());
		User updateUser = userService.getById(clientVO.getUpdateUser());
		clientVO.setCreateBy(createUser.getName());
		clientVO.setUpdateBy(updateUser.getName());
		return clientVO;
	}
}
