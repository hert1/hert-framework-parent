package com.hert.base.api.form.edit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hert
 * @create 2019/8/10
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleForm对象", description = "RoleForm对象")
public class RoleForm {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 父主键
     */
    @ApiModelProperty(value = "父主键")
    private Integer parentId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 角色别名
     */
    @ApiModelProperty(value = "角色别名")
    private String roleAlias;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private List<Integer> permissions;


}
