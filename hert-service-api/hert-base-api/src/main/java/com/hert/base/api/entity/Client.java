package com.hert.base.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hert.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("hert_client")
public class Client extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer id;

	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 客户端密钥
	 */
	private String clientSecret;
	/**
	 * 资源集合
	 */
	private String resourceIds;
	/**
	 * 授权范围
	 */
	private String scope;
	/**
	 * 授权类型
	 */
	private String authorizedGrantTypes;
	/**
	 * 令牌过期秒数
	 */
	private Integer accessTokenValidity;
	/**
	 * 刷新令牌过期秒数
	 */
	private Integer refreshTokenValidity;
	/**
	 * 附加说明
	 */
	private String additionalInformation;
	/**
	 * 自动授权
	 */
	private String autoapprove;
	/**
	 * 权限
	 */
	private String authorities;
	/**
	 * 回调地址
	 */
	private String webServerRedirectUri;


}
