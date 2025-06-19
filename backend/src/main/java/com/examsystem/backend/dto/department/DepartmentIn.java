package com.examsystem.backend.dto.department;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class DepartmentIn {
    private Integer id; // 部门ID，新增时可以不传

    @NotBlank(message = "部门名称不能为空")
    private String name; // 部门名称

    private String description; // 部门描述

    private Integer parentID; // 父部门ID，根部门为null

    private String type; // 部门类型（如学院、专业、班级等）
}
