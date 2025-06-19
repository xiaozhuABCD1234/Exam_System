package com.examsystem.backend.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Answer {
    private List<Integer> correctIndices; // 选择题
    private List<String> textAnswers; // 填空题
    private Boolean isTrue; // 判断题
    private List<Integer> correctOrder; // 排序题
}