package com.specification.mvc.control.handle;

import com.specification.mvc.vo.UserVO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zenggs
 * @Date 2022/11/14
 */
public class UserValidator implements ConstraintValidator<UserValidate, UserVO> {
    private static final String ADMIN_ID = "9999";
    @Override
    public boolean isValid(UserVO userVO, ConstraintValidatorContext constraintValidatorContext) {
        if (ADMIN_ID.equals(userVO.getUserId())){
            return true;
        }
        return false;
    }
}
