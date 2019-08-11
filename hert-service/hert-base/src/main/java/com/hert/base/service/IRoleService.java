package com.hert.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hert.base.api.entity.Role;
import com.hert.base.api.form.edit.RoleForm;
import com.hert.base.api.vo.RoleVO;
import com.hert.core.tool.node.INode;

import javax.validation.constraints.NotEmpty;
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
	 * 树形结构
	 *
	 * @param userId
	 * @return
	 */
	List<INode> tree(Integer userId);

	/**
	 * 新增或修改
	 */
	Boolean saveOrUpdate(RoleForm form);


}
