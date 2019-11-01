package com.hert.auth.form;

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
@ApiModel(value = "LoginForm对象", description = "LoginForm对象")
public class LoginForm {

    /**
     * 授权类型
     */
    @ApiModelProperty(value = "授权类型")
    private String grantType = "password";

    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;

    /**
     * 刷新令牌
     */
    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;


}
