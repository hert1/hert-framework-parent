package com.hert.base.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * YseOrNo枚举
 *
 * @author hert
 */
@Getter
@AllArgsConstructor
public enum YseOrNoEnum {

	/**
	 * 是
	 */
	YES("是", 1),

	/**
	 * 否
	 */
	NO("否", 2),
	;

	final String name;
	final Integer value;

	public static String getName(Integer value) {
		YseOrNoEnum[] enums = values();
		for (YseOrNoEnum ynenum : enums) {
			if (ynenum.value == value) {
				return ynenum.name;
			}
		}
		return null;
	}

}
