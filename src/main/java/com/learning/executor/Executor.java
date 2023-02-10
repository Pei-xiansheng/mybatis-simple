package com.learning.executor;



import com.learning.config.MappedStatement;

import java.util.List;

/**
 * @ClassName: Executor
 * @Description: 方法执行器，针对sqlsession对外提供的接口访问数据库执行具体的逻辑
 * @author: Apeng
 * @date: 2023/2/10 15:49
 */

public interface Executor {

    /**
     * 查询接口
     * 封装sql语句的mapperstatement对象
     * @Param 传入sql参数
     * @return
     **/
    <E> List<E> query(MappedStatement mappedStatement, Object parameter);

}
