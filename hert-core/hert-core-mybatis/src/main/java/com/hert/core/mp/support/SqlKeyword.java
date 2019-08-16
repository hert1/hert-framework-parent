package com.hert.core.mp.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hert.core.tool.utils.BeanUtil;
import com.hert.core.tool.utils.Func;
import com.hert.core.tool.utils.StringPool;
import com.hert.core.tool.utils.StringUtil;
import lombok.Getter;

import java.util.Map;

/**
 * 定义常用的 sql关键字
 *
 * @author hert
 */
public class SqlKeyword {
	public final static String SQL_REGEX = "'|%|--|insert|delete|update|select|count|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql";

	public static final String EQUAL = "_equal";
	public static final String NOT_EQUAL = "_notequal";
	public static final String LIKE = "_like";
	public static final String NOT_LIKE = "_notlike";
	public static final String GT = "_gt";
	public static final String LT = "_lt";
	public static final String DATE_GT = "_dategt";
	public static final String DATE_EQUAL = "_dateequal";
	public static final String DATE_LT = "_datelt";
	public static final String IS_NULL = "_null";
	public static final String NOT_NULL = "_notnull";
	public static final String IGNORE = "_ignore";

	/**
	 * 条件构造器
	 *
	 * @param query 查询字段
	 * @param qw    查询包装类
	 */
	public static void buildCondition(Object query, QueryWrapper<?> qw) {
		if (Func.isEmpty(query)) {
			return;
		}
		Map<String, Object> queryMap = BeanUtil.toMap(query);
		queryMap.remove("current");
		queryMap.remove("size");
        queryMap.forEach((k, v) -> {
			if (Func.hasEmpty(k, v) || k.endsWith(IGNORE)) {
				return;
			}
			if (k.endsWith(EQUAL)) {
				qw.eq(Func.isNotEmpty(v), getColumn(k, EQUAL), v);
			} else if (k.endsWith(NOT_EQUAL)) {
				qw.ne(Func.isNotEmpty(v), getColumn(k, NOT_EQUAL), v);
			} else if (k.endsWith(NOT_LIKE)) {
				qw.notLike(Func.isNotEmpty(v), getColumn(k, NOT_LIKE), v);
			} else if (k.endsWith(GT)) {
				qw.gt(Func.isNotEmpty(v), getColumn(k, GT), v);
			} else if (k.endsWith(LT)) {
				qw.lt(Func.isNotEmpty(v), getColumn(k, LT), v);
			} else if (k.endsWith(DATE_GT)) {
				qw.gt(Func.isNotEmpty(v), getColumn(k, DATE_GT), v);
			} else if (k.endsWith(DATE_EQUAL)) {
				qw.eq(Func.isNotEmpty(v), getColumn(k, DATE_EQUAL), v);
			} else if (k.endsWith(DATE_LT)) {
				qw.lt(Func.isNotEmpty(v), getColumn(k, DATE_LT), v);
			} else if (k.endsWith(IS_NULL)) {
				qw.isNull(Func.isNotEmpty(v), getColumn(k, IS_NULL));
			} else if (k.endsWith(NOT_NULL)) {
				qw.isNotNull(Func.isNotEmpty(v), getColumn(k, NOT_NULL));
			} else {
				qw.like(Func.isNotEmpty(v), getColumn(k, LIKE), v);
			}
		});
	}

	/**
	 * 获取数据库字段
	 *
	 * @param column  字段名
	 * @param keyword 关键字
	 * @return
	 */
	private static String getColumn(String column, String keyword) {
		return StringUtil.humpToUnderline(StringUtil.removeSuffix(column, keyword));
	}

	/**
	 * 把SQL关键字替换为空字符串
	 *
	 * @param param 关键字
	 * @return string
	 */
	public static String filter(String param) {
		if (param == null) {
			return null;
		}
		return param.replaceAll("(?i)" + SQL_REGEX, StringPool.EMPTY);
	}

}
