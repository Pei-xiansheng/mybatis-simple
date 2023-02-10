package com.learning.executor;

import com.learning.config.Configuration;
import com.learning.config.MappedStatement;
import com.learning.entity.AUser;
import com.sun.xml.internal.bind.v2.TODO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: DefaultExecutor
 * @Description:
 * @author: Apeng
 * @date: 2023/2/10 15:55
 */

public class DefaultExecutor implements Executor{

    private final Configuration configuration;

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object parameter) {
        //定义结果集
        List<E> ret = new ArrayList<>();
        try {
            //加载驱动
            Class.forName(configuration.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取连接
            connection = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcUsername(), configuration.getJdbcPassword());
            //创建预处理sql对象
            preparedStatement = connection.prepareStatement(mappedStatement.getSql());
            //处理sql中占位符信息
            parameterize(preparedStatement,parameter);
            //执行查询操作获取resultSet
            resultSet = preparedStatement.executeQuery();
            //将结果集通过反射填充对象中
            handlerResultSet(resultSet,ret,mappedStatement.getResultType());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return ret;
    }

    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String resultType) {
        //TODO 返回泛型结果集
        if(null==resultSet){
            return;
        }
        try {
            Class<?> resultClass = Class.forName(resultType);
            if(Collection.class.isAssignableFrom(resultClass)){
              while (resultSet.next()){
                  AUser user = new AUser();
                  user.setId(resultSet.getInt("id"));
                  user.setUsername(resultSet.getString("username"));
                  user.setEmail(resultSet.getString("email"));
                  user.setPhone(resultSet.getString("phone"));
                  ret.add((E) user);
              }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException {
        //TODO 支持更多类型填充占位符
        if(null!=parameter){
            if(parameter instanceof String){
                preparedStatement.setString(1,String.valueOf(parameter));
            }else if(parameter instanceof Integer){
                preparedStatement.setInt(1,Integer.parseInt(parameter.toString()));
            }
        }
    }
}
