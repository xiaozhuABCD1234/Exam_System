package com.examsystem.backend.dto.department;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
public class DepartmentOut {
    private Integer id; // 部门ID
    private String name; // 部门名称
    private String description; // 部门描述
    private Integer parentID; // 父部门ID，根部门为null
    private String type; // 部门类型（如学院、专业、班级等）
    private List<DepartmentOut> children; // 子部门列表，用于树形结构展示
}