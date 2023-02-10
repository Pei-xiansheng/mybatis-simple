package com.learning.session;

import java.util.List;

/**
 * @ClassName: SqlSession
 * @Description: 会话建立统一对外的crud外部接口
 * @author: Apeng
 * @date: 2023/2/10 14:49
 */

public interface SqlSession {

    /**
     * 条件查询单一返回结果
     **/
   <T> T selectOne(String statement,Object parameter);

    /**
     * 条件查询返回List结果
     **/
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 动态代理+反射 执行获取对应映射文件
     **/

    <T> T getMapper(Class<T> type);

}
