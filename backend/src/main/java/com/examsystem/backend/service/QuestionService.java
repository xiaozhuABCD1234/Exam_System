package com.examsystem.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examsystem.backend.dto.question.QuestionIn;
import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.entity.Question;
import com.examsystem.backend.entity.QuestionTag;
import com.examsystem.backend.entity.Tag;
import com.examsystem.backend.entity.User;
import com.examsystem.backend.repository.QuestionRepository;
import com.examsystem.backend.repository.TagRepository;
import com.examsystem.backend.utils.DtoUtils;
import com.examsystem.backend.utils.UserUtils;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public QuestionOut addQuestion(QuestionIn questionIn) {
        // 将 QuestionIn 转换为 Question 实体
        Question newQuestion = DtoUtils.convertToQuestion(questionIn);

        // 处理标签关联
        if (questionIn.getTagIDs() != null && !questionIn.getTagIDs().isEmpty()) {
            // 1. 查询所有标签
            List<Tag> tags = tagRepository.findAllById(questionIn.getTagIDs());
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

        // 设置创建和更新用户
        User created_by = UserUtils.getCurrentUser();
        newQuestion.setCreatedBy(created_by);
        newQuestion.setUpdatedBy(created_by);

        // 保存问题（级联保存关联的 QuestionTag）
        newQuestion = questionRepository.save(newQuestion);

        // 转换为 DTO
        QuestionOut questionOut = DtoUtils.questionToQuestionOut(newQuestion);

        return questionOut;
    }

    @Override
    public List<QuestionIn> addQuestions(List<QuestionIn> questions) {
        List<QuestionIn> failedQuestions = new ArrayList<>();
        for (QuestionIn questionIn : questions) {
            try {
                addQuestion(questionIn);
            } catch (Exception e) {
                failedQuestions.add(questionIn);
            }
        }
        return failedQuestions;
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public QuestionOut getQuestionById(Integer id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            return DtoUtils.questionToQuestionOut(question);
        }
        return null;
    }

    @Override
    public List<QuestionOut> getQuestionsCreatedByUid(String uid) {
        List<Question> questions = questionRepository.findByCreatedByUid(uid);
        return questions.stream()
                .map(DtoUtils::questionToQuestionOut)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionOut> getQuestionsUpdatedByUid(String uid) {
        List<Question> questions = questionRepository.findByUpdatedByUid(uid);
        return questions.stream()
                .map(DtoUtils::questionToQuestionOut)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionOut updateQuestion(QuestionIn question) {
        Optional<Question> questionOptional = questionRepository.findById(question.getId());
        if (questionOptional.isPresent()) {
            Question existingQuestion = questionOptional.get();
            // 复制非空属性
            BeanUtils.copyProperties(question, existingQuestion, getNullPropertyNames(question));

            // 处理标签关联
            if (question.getTagIDs() != null && !question.getTagIDs().isEmpty()) {
                // 1. 查询所有标签
                List<Tag> tags = tagRepository.findAllById(question.getTagIDs());
                // 2. 创建 QuestionTag 关联对象
                Set<QuestionTag> questionTags = new HashSet<>();
                for (Tag tag : tags) {
                    QuestionTag questionTag = new QuestionTag();
                    questionTag.setQuestion(existingQuestion);
                    questionTag.setTag(tag);
                    questionTags.add(questionTag);
                }
                // 3. 设置关联
                existingQuestion.setQuestionTags(questionTags);
            }

            // 设置更新用户
            User updatedBy = UserUtils.getCurrentUser();
            existingQuestion.setUpdatedBy(updatedBy);

            // 保存更新后的问题
            existingQuestion = questionRepository.save(existingQuestion);

            // 转换为 DTO
            return DtoUtils.questionToQuestionOut(existingQuestion);
        }
        return null;
    }

    private String[] getNullPropertyNames(Object source) {
        org.springframework.beans.BeanWrapper src = new org.springframework.beans.BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        java.util.Set<String> emptyNames = new java.util.HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
