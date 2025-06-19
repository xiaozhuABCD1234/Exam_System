package com.examsystem.backend.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

/**
 * 考试实体类，记录考试的基本信息，如考试名称、总分、时间等。
 */
@Entity
@Table(name = "exams")
@Data
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "exam_name", nullable = false, length = 255)
    private String examName;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    private Integer duration;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "uid")
    private User creator;

    @OneToMany(mappedBy = "exam")
    private Set<ExamQuestion> examQuestions;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}