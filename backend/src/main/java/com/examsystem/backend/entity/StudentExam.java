package com.examsystem.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 学生考试关联实体类，记录学生参加考试的详细信息
 */
@Entity
@Table(name = "student_exams")
@Data
public class StudentExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(nullable = false)
    private Boolean allowedToTake = false; // 是否允许参加考试

    @Column(nullable = true)
    private Double score; // 考试分数

}
