package com.examsystem.backend.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 课程ID

    @Column(name = "name", nullable = false)
    private String name; // 课程名称

    @Column(name = "code", nullable = false, unique = true)
    private String code; // 课程代码

    @Column(name = "credits", nullable = false)
    private Integer credits; // 学分

    @ManyToOne
    @Column(name = "department_id", nullable = false)
    private Department department; // 课程所属部门实体

    @ManyToOne
    @Column(name = "teacher_id", nullable = false)
    private User teacher; // 课程教师实体

    @ManyToMany(mappedBy = "courses")
    private List<User> students; // 选修该课程的学生列表

}
