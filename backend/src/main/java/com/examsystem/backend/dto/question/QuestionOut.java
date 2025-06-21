package com.examsystem.backend.dto.question;

import java.util.List;

import com.examsystem.backend.pojo.DifficultyLevel;
import com.examsystem.backend.pojo.QuestionType;

import lombok.Data;

@Data
public class QuestionOut {
    private Integer id; // 题目 ID
    private String content; // 题目内容
    private DifficultyLevel difficulty; // 题目难度，例如：简单、中等、困难
    private QuestionType type; // 题目类型，例如：单选、多选、判断
    private List<String> options; // 题目选项，使用 JSON 格式存储
    private Object answer; // 题目答案，使用 JSON 格式存储
    private String createdBy; // 创建者的用户 UID
    private String updatedBy; // 更新者的用户 UID
    private String createdAt; // 创建时间
    private String updatedAt; // 更新时间
    private List<Integer> tagIDs; // 题目标签 ID 列表，用于关联题目和标签
}
