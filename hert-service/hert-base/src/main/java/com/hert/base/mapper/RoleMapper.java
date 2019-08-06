package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.entity.Role;
import com.hert.base.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	List<RoleVO> selectRolePage(IPage page, RoleVO role);

	/**
	 * 获取树形节点
	 *
	 * @param tenantCode
	 * @param excludeRole
	 * @return
	 */
	List<RoleVO> tree(String tenantCode, String excludeRole);

	/**
	 * 查询角色通过用户id
	 *
	 * @param userId
	 * @return
	 */
	@Select("select r.* from hert_role r right join hert_user_role u on r.id = u.role_id where u.user_id = #{userId} and is_deleted = 0")
	List<Role> selectRoleByUserId(Integer userId);

}
