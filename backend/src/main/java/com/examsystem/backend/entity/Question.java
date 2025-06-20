package com.examsystem.backend.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.examsystem.backend.pojo.DifficultyLevel;
import com.examsystem.backend.pojo.QuestionType;

// import com.examsystem.backend.pojo.QuestionType;

import jakarta.persistence.*;
import lombok.*;

/**
 * 问题实体类，记录问题的基本信息，如问题内容、难度、类型等。
 */
@Entity
@Data
@Table(name = "questions")
@EqualsAndHashCode(of = "id") // 仅使用主键字段生成 equals 和 hashCode 方法
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "difficulty", nullable = true)
    private DifficultyLevel difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 50)
    private QuestionType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = true)
    private String options;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private String answer;

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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 在插入数据之前设置当前时间
        updatedAt = LocalDateTime.now(); // 在插入数据之前设置当前时间
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // 在更新数据之前设置当前时间
    }
}