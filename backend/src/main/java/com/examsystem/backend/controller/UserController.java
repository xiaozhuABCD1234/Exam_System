package com.examsystem.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examsystem.backend.dto.user.UserIn;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.entity.User;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseMessage<UserOut> createUser(@Validated @RequestBody UserIn dataIn) {
        UserOut newUser = userService.addUser(dataIn);
        if (newUser == null) {
            return ResponseMessage.error(502, "创建用户失败");
        }
        return ResponseMessage.success(newUser);
    }

    @GetMapping("/{uid}")
    public ResponseMessage<UserOut> getUserByUid(@PathVariable String uid) {
        UserOut user = userService.getUserByUid(uid);
        if (user == null) {
            return ResponseMessage.error(404, "用户未找到");
        }
        return ResponseMessage.success(user);
    }

}
