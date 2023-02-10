package com.learning.session;

import com.learning.config.Configuration;
import com.learning.config.MappedStatement;
import com.learning.executor.DefaultExecutor;
import com.learning.executor.Executor;
import com.learning.proxy.MapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;


/**
 * @ClassName: DefaultSqlSession
 * @Description:
 * @author: Apeng
 * @date: 2023/2/10 14:54
 */

public class DefaultSqlSession implements SqlSession{

    private final Configuration configuration;

    private Executor executor;



    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new DefaultExecutor(configuration);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        //执行sql语句查询结果
        List<Object> selectList = this.selectList(statement, parameter);
        //查询结果
        if(null==selectList||selectList.size()==0){
            return null;
        }
        if(selectList.size()==1){
            return (T) selectList.get(0);
        }else {
            throw new RuntimeException("Only want to get One But too many Results!");
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statement);
        return executor.query(mappedStatement,parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},mapperProxy);
    }
}
