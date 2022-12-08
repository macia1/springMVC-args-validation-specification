package com.specification.mvc.control;

import com.specification.mvc.control.handle.UserValidate;
import com.specification.mvc.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zenggs
 * @Date 2022/11/9
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserControl {

    @PostMapping
    public ResponseEntity<String> addUser(@Validated @RequestBody UserVO userVO){
        log.info("新增用户信息入参:{}", userVO.toString());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> query(@RequestParam(name = "userId") String userId){
        log.info("获取用户信息入参:{}", userId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
