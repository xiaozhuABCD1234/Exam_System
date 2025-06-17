package com.examsystem.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIn {
    @NotBlank(message = "uid不能为空")
    private String uid;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private Integer roleID; // 权限ID
    private Integer departmentID; // 部门ID
}
