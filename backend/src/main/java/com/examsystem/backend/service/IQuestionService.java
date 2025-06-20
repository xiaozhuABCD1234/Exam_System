package com.examsystem.backend.service;

import com.examsystem.backend.dto.question.QuestionIn;
import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.entity.Question;
import java.util.List;

public interface IQuestionService {
    QuestionOut addQuestion(QuestionIn question);

    QuestionOut getQuestionById(Integer id);

    QuestionOut updateQuestion(QuestionIn question);

    boolean deleteQuestion(Integer id);

    List<QuestionOut> getQuestionsCreatedByUid(String uid);

    List<QuestionOut> getQuestionsUpdatedByUid(String uid);

    List<QuestionIn> addQuestions(List<QuestionIn> questions);// 批量添加题目，返回失败的对象
}
