package com.learning.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Configuration
 * @Description: 用于存放配置信息（实现mybatis容器）
 * @author: Apeng
 * @date: 2023/2/10 1:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    /**
     * 数据库连接
     **/
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;


    /**
     * 用于存放对应xml文件中sql信息
     **/
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<String,MappedStatement>();
}
