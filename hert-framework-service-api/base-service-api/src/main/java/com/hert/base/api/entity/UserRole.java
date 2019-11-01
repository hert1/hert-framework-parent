package com.hert.base.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hert.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("hert_user_role")
@Builder
public class UserRole {

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
	private Integer roleId;


}
