package com.hert.base.api.form.edit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
@ApiModel(value = "EditForm对象", description = "EditForm对象")
public class EditForm {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id集合")
    @JsonProperty(value = "ids")
    private List<Integer> idList;

}
