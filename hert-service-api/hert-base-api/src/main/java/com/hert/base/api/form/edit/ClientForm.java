package com.hert.base.api.form.edit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hert
 * @create 2019/8/10
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClientForm对象", description = "ClientForm对象")
public class ClientForm {

    /**
     * 主键id
     */
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
    /**
     * 状态
     */
    private Integer status;

}
