package com.hert.base.api.form.edit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "UserForm对象", description = "UserForm对象")
public class UserForm {

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
     * 密码
     */
    private String password;
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
     * 生日
     */
    private LocalDateTime birthday;
    /**
     * 上级id
     */
    private Integer parentId;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 账号类型
     */
    private Integer accountType;
    /**
     * 部门
     */
    private List<Integer> depts;
    /**
     * 角色
     */
    private List<Integer> roles;


}
