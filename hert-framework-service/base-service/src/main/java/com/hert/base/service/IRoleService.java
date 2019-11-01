package com.hert.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hert.base.api.entity.Role;
import com.hert.base.api.form.edit.RoleForm;
import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IRoleService extends IService<Role> {


	/**
	 * 查询角色通过用户id
	 *
	 * @param userId
	 * @return
	 */
	List<Role> selectRoleByUserId(Integer userId);


	/**
	 * 新增或修改
	 */
	Boolean saveOrUpdate(RoleForm form);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String roleIds);


}
