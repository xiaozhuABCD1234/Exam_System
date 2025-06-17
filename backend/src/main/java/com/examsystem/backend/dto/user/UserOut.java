package com.examsystem.backend.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserOut {
    private String uid;
    private String username;
    private Integer roleID; // 权限ID
    private Integer departmentID; // 部门ID
    private String roleName; // 权限名称
    private String departmentName; // 部门名称
}
