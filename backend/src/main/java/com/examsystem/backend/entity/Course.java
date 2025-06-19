package com.examsystem.backend.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 课程实体类，记录课程的基本信息，如课程名称、代码、学分等。
 */
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
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // 课程所属的院系实体

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "uid", nullable = false)
    private User teacher; // 课程教师实体


    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourses; // 选修该课程的学生列表

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 课程开始日期

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 课程结束日期
}