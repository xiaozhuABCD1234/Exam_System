package com.examsystem.backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.examsystem.backend.dto.question.QuestionIn;
import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.entity.Question;
import com.examsystem.backend.entity.QuestionTag;
import com.examsystem.backend.entity.Tag;
import com.examsystem.backend.entity.User;
import com.examsystem.backend.repository.QuestionRepository;
import com.examsystem.backend.repository.TagRepository;
import com.examsystem.backend.utils.UserUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuestionService implements IQuestionService {

    // 这里可以注入 QuestionRepository 和 TagRepository
    // private final QuestionRepository questionRepository;
    // private final TagRepository tagRepository;

    // public QuestionService(QuestionRepository questionRepository, TagRepository
    // tagRepository) {
    // this.questionRepository = questionRepository;
    // this.tagRepository = tagRepository;
    // }
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public QuestionOut addQuestion(QuestionIn question) {
        Question newQuestion = new Question();
        // 复制 QuestionIn 的属性到 Question 实体
        BeanUtils.copyProperties(question, newQuestion);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (question.getOptions() != null) {
                newQuestion.setOptions(objectMapper.writeValueAsString(question.getOptions()));
            }
            if (question.getAnswer() != null) {
                newQuestion.setAnswer(objectMapper.writeValueAsString(question.getAnswer()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert options or answer to JSON", e);
        }

        User created_by = UserUtils.getCurrentUser();
        newQuestion.setCreatedBy(created_by);
        newQuestion.setUpdatedBy(created_by);

        // 处理标签关联
        if (question.getTagIDs() != null && !question.getTagIDs().isEmpty()) {
            // 1. 查询所有标签
            List<Tag> tags = tagRepository.findAllById(question.getTagIDs());

            // 2. 创建 QuestionTag 关联对象
            Set<QuestionTag> questionTags = new HashSet<>();
            for (Tag tag : tags) {
                QuestionTag questionTag = new QuestionTag();
                questionTag.setQuestion(newQuestion);
                questionTag.setTag(tag);
                questionTags.add(questionTag);
            }

            // 3. 设置关联
            newQuestion.setQuestionTags(questionTags);
        }

        // 保存问题（级联保存关联的 QuestionTag）
        newQuestion = questionRepository.save(newQuestion);

        // 转换为 DTO 返回
        QuestionOut questionOut = new QuestionOut();
        BeanUtils.copyProperties(newQuestion, questionOut);
        // 将 JSON 字符串反序列化为 List<String> 或 List<?>
        try {
            if (newQuestion.getOptions() != null) {
                questionOut
                        .setOptions(objectMapper.readValue(newQuestion.getOptions(), new TypeReference<List<String>>() {
                        }));
            }
            if (newQuestion.getAnswer() != null) {
                questionOut.setAnswer(objectMapper.readValue(newQuestion.getAnswer(), Object.class));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert options or answer from JSON", e);
        }
        // 可选：设置标签 ID 列表到 DTO
        if (newQuestion.getQuestionTags() != null) {
            List<Integer> tagIds = newQuestion.getQuestionTags().stream()
                    .map(QuestionTag::getTag)
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            questionOut.setTagIDs(tagIds);
        }

        return questionOut;
    }

    @Override
    public List<QuestionIn> addQuestions(List<QuestionIn> questions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public QuestionOut getQuestionById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<QuestionOut> getQuestionsCreatedByUid(String uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<QuestionOut> getQuestionsUpdatedByUid(String uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QuestionOut updateQuestion(QuestionIn question) {
        // TODO Auto-generated method stub
        return null;
    }

}
