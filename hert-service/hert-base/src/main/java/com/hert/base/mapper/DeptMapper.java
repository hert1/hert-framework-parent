package com.hert.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hert.base.api.entity.Dept;
import com.hert.base.api.vo.DeptVO;
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
	 * 查询部门通过用户id
	 *
	 * @param userId
	 * @return
	 */
	@Select("select d.* from hert_dept d right join hert_user_dept u on d.id = u.dept_id where u.user_id = #{userId} and is_deleted = 0")
	List<Dept> selectDeptByUserId(Integer userId);

}
