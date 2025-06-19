package com.examsystem.backend.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; // 标签ID

    @Column(name = "name", nullable = false, unique = true)
    private String name; // 标签名称

    @OneToMany(mappedBy = "tag")
    private Set<QuestionTag> questionTags;

}
