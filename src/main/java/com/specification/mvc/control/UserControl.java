package com.specification.mvc.control;

import com.specification.mvc.annotation.Authorization;
import com.specification.mvc.annotation.UserToken;
import com.specification.mvc.dto.UserInfo;
import com.specification.mvc.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @Authorization
    @PostMapping("/manager")
    public ResponseEntity<UserInfo> query(@UserToken UserInfo userInfo,Object a){
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @Authorization
    @GetMapping("/preview")
    public ResponseEntity<byte[]> preView(){
        try {
            String path = "C:\\Users\\zengguosheng\\Desktop\\Books\\the-fenix-project (1).pdf";
            String fileName = path.substring(path.lastIndexOf(File.separator)+1);
            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            httpHeaders.setContentDispositionFormData("inline", new String(fileName.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));
            return new ResponseEntity<>(Files.readAllBytes(Paths.get(path)) ,httpHeaders, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
