package com.hert.base.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("hert_user_dept")
@Builder
public class UserDept {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 角色id
	 */
	private Integer deptId;


}
