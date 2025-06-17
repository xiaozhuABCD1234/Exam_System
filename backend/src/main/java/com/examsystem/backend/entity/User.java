package com.examsystem.backend.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_number", nullable = false, unique = true)
    private String identityNumber;// 用户的学号\工号

    @Column(name = "username", nullable = false)
    private String username;// 学生老师的真实姓名

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @Column(name = "role_id", nullable = false)
    private Role role; // 权限

    @ManyToOne
    @Column(name = "department_id", nullable = false)
    private Department department; // 部门实体

    @ManyToMany()
    @JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses; // 选修的课程列表

}
