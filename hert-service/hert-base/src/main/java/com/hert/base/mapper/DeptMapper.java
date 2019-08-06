package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.entity.Dept;
import com.hert.base.vo.DeptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	List<DeptVO> selectDeptPage(IPage page, DeptVO dept);

	/**
	 * 获取树形节点
	 *
	 * @param tenantCode
	 * @return
	 */
	List<DeptVO> tree(String tenantCode);

	/**
	 * 查询角色通过用户id
	 *
	 * @param userId
	 * @return
	 */
	@Select("select d.* from hert_dept d right join hert_user_dept u on d.id = u.dept_id where u.user_id = #{userId}")
	List<Dept> selectDeptByUserId(Integer userId);

}
