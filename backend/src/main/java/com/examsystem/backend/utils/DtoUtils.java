package com.examsystem.backend.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.examsystem.backend.dto.question.QuestionIn;
import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.entity.Question;
import com.examsystem.backend.entity.QuestionTag;
import com.examsystem.backend.entity.Tag;
import com.examsystem.backend.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DtoUtils {
    public static UserOut UserToUserOutput(User user) {
        return UserOut.builder()
                .uid(user.getUid())
                .username(user.getUsername())
                .roleID(user.getRole().getId())
                .roleName(user.getRole().getName())
                .departmentID(user.getDepartment() != null ? user.getDepartment().getId() : null)
                .departmentName(user.getDepartment() != null ? user.getDepartment().getName() : null)
                .build();
    }

    /**
     * 将 Question 实体转换为 QuestionOut DTO
     *
     * @param question Question 实体
     * @return QuestionOut DTO
     */
    public static QuestionOut questionToQuestionOut(Question question) {
        QuestionOut questionOut = new QuestionOut();
        BeanUtils.copyProperties(question, questionOut);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (question.getOptions() != null) {
                questionOut.setOptions(objectMapper.readValue(question.getOptions(), new TypeReference<List<String>>() {
                }));
            }
            if (question.getAnswer() != null) {
                questionOut.setAnswer(objectMapper.readValue(question.getAnswer(), Object.class));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert options or answer from JSON", e);
        }

        // 可选：设置标签 ID 列表到 DTO
        if (question.getQuestionTags() != null) {
            List<Integer> tagIds = question.getQuestionTags().stream()
                    .map(QuestionTag::getTag)
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            questionOut.setTagIDs(tagIds);
        }

        // 设置创建和更新用户的 UID
        User createdBy = question.getCreatedBy();
        if (createdBy != null) {
            questionOut.setCreatedBy(createdBy.getUid());
            questionOut.setUpdatedBy(createdBy.getUid());
        }

        // 设置创建和更新时间
        questionOut.setCreatedAt(question.getCreatedAt().toString());
        questionOut.setUpdatedAt(question.getUpdatedAt().toString());

        return questionOut;
    }

    /**
     * 将 QuestionIn 转换为 Question 实体
     *
     * @param questionIn QuestionIn 对象
     * @return Question 实体
     */
    public static Question convertToQuestion(QuestionIn questionIn) {
        Question question = new Question();
        BeanUtils.copyProperties(questionIn, question);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (questionIn.getOptions() != null) {
                question.setOptions(objectMapper.writeValueAsString(questionIn.getOptions()));
            }
            if (questionIn.getAnswer() != null) {
                question.setAnswer(objectMapper.writeValueAsString(questionIn.getAnswer()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert options or answer to JSON", e);
        }

        return question;
    }

}
