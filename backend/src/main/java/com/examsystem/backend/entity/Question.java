package com.examsystem.backend.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

// import com.examsystem.backend.pojo.QuestionType;

import jakarta.persistence.*;
import lombok.*;

/**
 * 问题实体类，记录问题的基本信息，如问题内容、难度、类型等。
 */
@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "content", nullable = false)
    private String content; // 题目内容

    @Column(name = "difficulty", nullable = true)
    private DifficultyLevel difficulty; // 题目难度，例如：简单、中等、困难

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "uid", nullable = true)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "uid", nullable = true)
    private User updatedBy;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<QuestionTag> questionTags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private QuestionType type; // 使用枚举类型

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> options;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Answer answer;

    public enum DifficultyLevel {
        EASY, MEDIUM, HARD
    }


    public enum QuestionType {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE,
        FILL_BLANK,
        TRUE_FALSE,
        MULTIPLE_FILL_BLANKS,
        ORDERING,
        MATCHING // 可扩展匹配题
    }
}
