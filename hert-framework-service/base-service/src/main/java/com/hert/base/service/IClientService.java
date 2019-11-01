package com.hert.base.service;


import com.hert.base.api.dto.UserDTO;
import com.hert.base.api.entity.Client;
import com.hert.base.api.entity.User;
import com.hert.base.api.form.edit.ClientForm;
import com.hert.base.api.form.edit.UserForm;
import com.hert.core.mp.base.BaseService;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IClientService extends BaseService<Client> {

	/**
	 * 新增或修改客户端
	 *
	 * @param form
	 * @return
	 */
	boolean submit(ClientForm form);


}
