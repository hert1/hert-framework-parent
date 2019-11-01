package com.hert.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hert.base.api.entity.Dept;
import com.hert.base.api.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IDeptService extends IService<Dept> {


	/**
	 * 查询部门通过用户id
	 *
	 * @param userId
	 * @return
	 */
	List<Dept> selectDeptByUserId(Integer userId);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptName(String deptIds);
}
