package com.hert.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hert.base.api.entity.Dept;
import com.hert.base.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept);

	/**
	 * 树形结构
	 *
	 * @param tenantCode
	 * @return
	 */
	List<DeptVO> tree(String tenantCode);

	/**
	 * 查询部门通过用户id
	 *
	 * @param userId
	 * @return
	 */
	List<Dept> selectDeptByUserId(Integer userId);
}
