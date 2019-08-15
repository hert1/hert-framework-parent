package com.hert.base.api.provider;

import org.apache.ibatis.annotations.Param;

/**
 * @author hert
 * @create 2019/8/15
 * @since 1.0.0
 */
public class UserProvider {

    public String selectByQuery(@Param("query") UserQuery query) {
        String sql = "select u.* from hert_user u where u.id = #{query.id} and u.is_deleted = 0";
        return sql;
    }

}
