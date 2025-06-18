package com.examsystem.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.IUserService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private IUserService userService;

    @GetMapping("{id}")
    public ResponseMessage<List<UserOut>> getUsersByRole(@PathVariable Integer id) {
        List<UserOut> users = userService.getUsersByRole(id);

        if (users == null || users.isEmpty()) {
            return ResponseMessage.error(404, "没有找到该角色的用户");
        }

        return ResponseMessage.success(users);
    }
}
