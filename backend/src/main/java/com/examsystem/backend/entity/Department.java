package com.examsystem.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Column;

/**
 * 部门实体类，记录部门的基本信息，如部门名称、类型等，并支持层级关系。
 */
@Entity
@Data
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type; // 部门类型（如学院、专业、班级等）

    @ManyToOne
    @JoinColumn(name = "parent_department_id", unique = false, nullable = true)
    private Department parentDepartment; // 父部门，用于表示层级关系

    // 一对多关联，指向子部门（关系的反向方，使用mappedBy）
    @OneToMany(mappedBy = "parentDepartment")
    private List<Department> childDepartments;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY) // 使用懒加载以提高性能
    private List<User> users; // 关联的用户列表

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY) // 使用懒加载以提高性能
    private List<Course> courses; // 关联的课程列表
}
