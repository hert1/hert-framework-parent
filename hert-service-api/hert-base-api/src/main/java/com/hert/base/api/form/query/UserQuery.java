package com.hert.base.api.form.query;

import com.hert.core.mp.support.Query;
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
@ApiModel(value = "UserQuery对象", description = "UserQuery对象")
public class UserQuery extends Query {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Integer id;

    /**
     * 账号
     */
    private String account;
    /**
     * 昵称
     */
    private String name;
    /**
     * 真名
     */
    private String realName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String phone;
    /**
     * 性别
     */
    private Integer sex;

    /**
     * 账号类型
     */
    private Integer accountType;

    /**
     * 部门
     */
    private List<Integer> dept;

    /**
     * 角色
     */
    private List<Integer> role;


}
