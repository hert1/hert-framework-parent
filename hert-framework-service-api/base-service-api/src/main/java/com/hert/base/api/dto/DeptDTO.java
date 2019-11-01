package com.hert.base.api.dto;

import com.hert.base.api.entity.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends Dept {
	private static final long serialVersionUID = 1L;

}
