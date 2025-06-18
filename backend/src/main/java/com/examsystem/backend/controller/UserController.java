package com.examsystem.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examsystem.backend.dto.user.UserIn;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.IUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/username/{username}")
    public ResponseMessage<List<UserOut>> getUserByUsername(@PathVariable String username) {
        List<UserOut> user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseMessage.error(404, "用户未找到");
        }
        return ResponseMessage.success(user);
    }

    @PutMapping()
    public ResponseMessage<UserOut> updateUser(@Validated @RequestBody UserIn dataIn) {
        UserOut updatedUser = userService.updateUser(dataIn);
        if (updatedUser == null) {
            return ResponseMessage.error(502, "更新用户失败");
        }
        return ResponseMessage.success(updatedUser);
    }

    @DeleteMapping("/{uid}")
    public ResponseMessage<String> deleteUser(@PathVariable String uid) {
        boolean isDeleted = userService.deleteUser(uid);
        if (!isDeleted) {
            return ResponseMessage.error(502, "删除用户失败");
        }
        return ResponseMessage.success("用户删除成功");
    }
}
