package com.hert.base.api.form.edit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hert.core.tool.utils.Func;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hert
 * @create 2019/8/10
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Menu对象", description = "Menu对象")
public class MenuForm {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单父主键
     */
    @ApiModelProperty(value = "菜单父主键")
    private Integer parentId;

    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String code;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;


    /**
     * 菜单logo
     */
    @ApiModelProperty(value = "菜单logo")
    private String icon;

    /**
     * 是否缓存
     */
    @ApiModelProperty(value = "是否缓存")
    private Long isCache;

    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String path;

    /**
     * 菜单资源
     */
    @ApiModelProperty(value = "菜单资源")
    private String source;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型")
    private Integer category;

    /**
     * 操作按钮类型
     */
    @ApiModelProperty(value = "操作按钮类型")
    private Integer action;

    /**
     * 是否打开新页面
     */
    @ApiModelProperty(value = "是否打开新页面")
    private Integer isOpen;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;



}
