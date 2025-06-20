package com.examsystem.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_exam_answers")
@Data
public class StudentExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 主键ID

    @ManyToOne
    @JoinColumn(name = "student_exam_id", nullable = false)
    private StudentExam studentExam; // 关联的学生考试记录

    @ManyToOne
    @JoinColumn(name = "exam_question_id", nullable = false)
    private ExamQuestion examQuestion; // 关联的考试问题

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String answer; // 学生的答案内容，使用JSONB存储

    @Column(name = "score", nullable = true)
    private Double score; // 学生该题得分

    @Column(name = "is_correct", nullable = true)
    private Boolean isCorrect; // 是否回答正确

    @Column(name = "answered_at", nullable = false)
    private LocalDateTime answeredAt; // 回答时间
}
