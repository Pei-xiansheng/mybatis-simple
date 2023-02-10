package com.learning.mapper;

import com.learning.entity.AUser;

import java.util.List;

/**
 * @ClassName: AUserMapper
 * @Description:
 * @author: Apeng
 * @date: 2023/2/10 15:07
 */

public interface AUserMapper {

    AUser selectOne(Integer id);

    List<AUser> selectAll();
}
