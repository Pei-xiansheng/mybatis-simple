package com.learning.reflect;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: ReflectionUtil
 * @Description: 反射工具类
 * @author: Apeng
 * @date: 2023/2/10 16:38
 */

public class ReflectionUtil {

    /**
     *  为指定的bean的proName属性值设为value
     * @Param bean 目标对象
     * @Param propName 对象的属性
     * @return value 值
     **/
    public static void setPropToBean(Object bean,String propName,Object value){
        Field f;
        //获取指定的属性
        try {
            f = bean.getClass().getDeclaredField(propName);
            //设置字段为可铜鼓哟反射进行访问
            f.setAccessible(true);
            f.set(bean,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *  从resultSet中读取一行数据，并填充指定的实体bean
     * @Param entity 待填充的实体bean
     * @Param resultSet 从数据库中加载的数据
     * @return
     **/
    public static void setPropToBeanFromResultSet(Object entity, ResultSet resultSet) throws SQLException {
        //反射获取对象的字段
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if(declaredFields[i].getType().getSimpleName().equals("String")){
                setPropToBean(entity,declaredFields[i].getName(),resultSet.getString(declaredFields[i].getName()));
            }else if(declaredFields[i].getType().getSimpleName().equals("Integer")){
                setPropToBean(entity,declaredFields[i].getName(),resultSet.getInt(declaredFields[i].getName()));
            }else if(declaredFields[i].getType().getSimpleName().equals("Long")){
                setPropToBean(entity,declaredFields[i].getName(),resultSet.getLong(declaredFields[i].getName()));
            }
        }
    }
}
