package com.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: AUser
 * @Description:
 * @author: Apeng
 * @date: 2023/2/10 15:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AUser {
    private Integer id;
    private String username;
    private String email;
    private String phone;
}
