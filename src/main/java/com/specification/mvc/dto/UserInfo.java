package com.specification.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zenggs
 * @Date 2022/12/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String userName;
    private String mobile;
    private String email;

}
