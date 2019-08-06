package com.hert.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hert.base.api.entity.Role;
import com.hert.base.vo.RoleVO;
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
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

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
	 * 权限配置
	 *
	 * @param roleIds 角色id集合
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Integer> roleIds, @NotEmpty List<Integer> menuIds);

}
