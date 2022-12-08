package com.specification.mvc.vo;

import com.specification.mvc.control.handle.UserValidate;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author zenggs
 * @Date 2022/11/9
 */
@Data
public class UserVO {
    @NotBlank
    private String userName;
    @Email
    private String email;
    @NotBlank
    private String userId;

}
