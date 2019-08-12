/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hert.base.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.form.edit.UserForm;
import com.hert.core.mp.base.BaseService;
import com.hert.base.api.entity.User;
import com.hert.base.api.dto.UserDTO;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户
	 *
	 * @param form
	 * @return
	 */
	boolean submit(UserForm form);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserDTO userInfo(Integer userId);

	/**
	 * 用户信息
	 *
	 * @param account
	 * @param password
	 * @return
	 */
	UserDTO userInfo(String account, String password);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

}
