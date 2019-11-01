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
@ApiModel(value = "DeptForm对象", description = "DeptForm对象")
public class DeptForm {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父主键
     */
    @ApiModelProperty(value = "父主键")
    private Integer parentId;

    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名")
    private String deptName;

    /**
     * 部门全称
     */
    @ApiModelProperty(value = "部门全称")
    private String fullName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;


}
