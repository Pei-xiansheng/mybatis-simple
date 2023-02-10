package com.learning;

import com.learning.entity.AUser;
import com.learning.mapper.AUserMapper;
import com.learning.session.SqlSession;
import com.learning.session.SqlSessionFactory;

import java.util.List;

/**
 * Hello world!
 *
 */
public class MyBatisLearningApplication {
    public static void main( String[] args ) {
        //1、例化SqlSessionFactory 加载数据库配置文件以及对应mapper。xml文件到configuration中
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        //2、获取sqlsession对象
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        //3、通过i动态代理获取代理类
        AUserMapper userMapper = sqlSession.getMapper(AUserMapper.class);
        //4、通过jdbc将数据与对象绑定
        AUser selectOne = userMapper.selectOne(1);
        System.out.println("查询单条数据"+selectOne);
        List<AUser> selectAll = userMapper.selectAll();
        for (AUser au:selectAll
             ) {
            System.out.println("查询多条数据"+selectOne);

        }
    }
}
