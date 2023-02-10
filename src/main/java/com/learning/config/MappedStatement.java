package com.learning.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: MappedStatement
 * @Description: 用于存储Mapper映射信息
 * @author: Apeng
 * @date: 2023/2/10 1:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {
    private String namespace;
    private String sourceId;
    private String resultType;
    private String sql;
}
