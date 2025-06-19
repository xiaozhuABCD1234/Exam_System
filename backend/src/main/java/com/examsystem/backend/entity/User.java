package com.examsystem.backend.entity;

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
 * 用户实体类，记录用户的基本信息，如学号/工号、姓名、密码等。
 */
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uid", nullable = false, unique = true, updatable = false, length = 255)
    private String uid;// 用户的学号/工号，作为业务唯一标识

    @Column(name = "username", nullable = false, length = 255)
    private String username;// 学生老师的真实姓名(可以重复)

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // 权限

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // 部门实体

    @OneToMany(mappedBy = "teacher")
    private List<Course> taughtCourses; // 教授的课程列表

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses; // 选修的课程列表

    @OneToMany(mappedBy = "student")
    private List<StudentExam> studentExams; // 参加的考试列表

    @OneToMany(mappedBy = "createdBy")
    private List<Question> createdQuestions; // 创建的问题列表

    @OneToMany(mappedBy = "updatedBy")
    private List<Question> updatedQuestions; // 更新的问题列表

    @OneToMany(mappedBy = "creator")
    private List<Exam> createdExams; // 创建的考试列表
}
