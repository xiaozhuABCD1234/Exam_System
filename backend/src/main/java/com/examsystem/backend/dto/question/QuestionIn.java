package com.examsystem.backend.dto.question;

import java.util.List;

import com.examsystem.backend.pojo.DifficultyLevel;
import com.examsystem.backend.pojo.QuestionType;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionIn {
    private Integer id;
    @NotBlank(message = "题目内容不能为空")
    private String content; // 题目内容
    private DifficultyLevel difficulty; // 题目难度，例如：简单、中等、困难
    private QuestionType type; // 题目类型，例如：单选、多选、判断等
    private List<String> options; // 题目选项，使用 JSON 格式存储
    private List<?> answer; // 题目答案，使用 JSON 格式存储
    private List<Integer> tagIDs;
}
