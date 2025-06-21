package com.examsystem.backend.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 问题标签关联实体类，用于关联问题和标签。
 */
@Entity
@Data
@Table(name = "question_tags")
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuestionTag that = (QuestionTag) o;
        return question.getId().equals(that.question.getId()) &&
                tag.getId().equals(that.tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(question.getId(), tag.getId());
    }
}