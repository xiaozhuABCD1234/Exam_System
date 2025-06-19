package com.examsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.examsystem.backend.dto.department.*;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.IDepartmentService;
import com.examsystem.backend.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IUserService userService;

    // 创建部门
    @PostMapping()
    public ResponseMessage<DepartmentOut> createDepartment(@Validated @RequestBody DepartmentIn dataIn) {
        DepartmentOut newDepartment = departmentService.addDepartment(dataIn);
        if (newDepartment == null) {
            return ResponseMessage.error(502, "创建部门失败");
        }
        return ResponseMessage.success(newDepartment);
    }

    // 根据部门ID删除部门
    @DeleteMapping("/{id}")
    public ResponseMessage<String> deleteDepartment(@PathVariable Integer id) {
        boolean isDeleted = departmentService.deleteDepartment(id);
        if (!isDeleted) {
            return ResponseMessage.error(502, "删除部门失败");
        }
        return ResponseMessage.success("部门删除成功");
    }

    // 根据部门ID查询部门
    @GetMapping("/{id}")
    public ResponseMessage<DepartmentOut> getDepartmentById(@PathVariable Integer id) {
        DepartmentOut department = departmentService.getDepartmentById(id);
        if (department == null) {
            return ResponseMessage.error(404, "部门未找到");
        }
        return ResponseMessage.success(department);
    }

    // 更新部门信息
    @PutMapping()
    public ResponseMessage<DepartmentOut> updateDepartment(@Validated @RequestBody DepartmentIn dataIn) {
        DepartmentOut updatedDepartment = departmentService.updateDepartment(dataIn);
        if (updatedDepartment == null) {
            return ResponseMessage.error(502, "更新部门失败");
        }
        return ResponseMessage.success(updatedDepartment);
    }

    // 根据部门ID查询下面所有的用户
    @GetMapping("/{id}/users")
    public ResponseMessage<List<UserOut>> getUsersByDepartmentId(@PathVariable Integer id) {
        List<UserOut> users = userService.getUsersByDepartmentId(id);
        if (users == null || users.isEmpty()) {
            return ResponseMessage.error(404, "该部门下未找到用户");
        }
        return ResponseMessage.success(users);
    }
}
