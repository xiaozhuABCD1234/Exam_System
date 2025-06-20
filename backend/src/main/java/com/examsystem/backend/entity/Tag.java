package com.examsystem.backend.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "tags")
@EqualsAndHashCode(of = "id") // 仅使用主键字段生成 equals 和 hashCode 方法
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<QuestionTag> questionTags;
}