package com.examsystem.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 学生选课记录实体类，记录学生选修课程的信息和成绩。
 */
@Entity
@Table(name = "student_courses")
@Data
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 选课记录ID

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "uid", nullable = false)
    private User student; // 学生实体

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course; // 课程实体

    @Column(name = "grade", nullable = true)
    private Double grade; // 学生成绩，可能为null表示未评分
}
