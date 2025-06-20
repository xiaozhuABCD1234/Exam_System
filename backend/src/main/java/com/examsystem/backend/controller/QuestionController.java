package com.examsystem.backend.controller;

import com.examsystem.backend.dto.question.QuestionIn;
import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 添加单个题目
     * 
     * @param question 题目输入信息
     * @return 包含题目输出信息的响应消息
     */
    @PostMapping()
    public ResponseMessage<QuestionOut> addQuestion(@Validated @RequestBody QuestionIn question) {
        QuestionOut newQuestion = questionService.addQuestion(question);
        if (newQuestion == null) {
            return ResponseMessage.error(502, "创建题目失败");
        }
        return ResponseMessage.success(newQuestion);
    }

    /**
     * 根据题目 ID 获取题目信息
     * 
     * @param id 题目 ID
     * @return 包含题目输出信息的响应消息
     */
    @GetMapping("/{id}")
    public ResponseMessage<QuestionOut> getQuestionById(@PathVariable Integer id) {
        QuestionOut question = questionService.getQuestionById(id);
        if (question == null) {
            return ResponseMessage.error(404, "题目未找到");
        }
        return ResponseMessage.success(question);
    }

    /**
     * 更新题目信息
     * 
     * @param question 题目输入信息
     * @return 包含更新后题目输出信息的响应消息
     */
    @PutMapping()
    public ResponseMessage<QuestionOut> updateQuestion(@RequestBody QuestionIn question) {
        QuestionOut updatedQuestion = questionService.updateQuestion(question);
        if (updatedQuestion == null) {
            return ResponseMessage.error(502, "更新题目失败");
        }
        return ResponseMessage.success(updatedQuestion);
    }

    /**
     * 根据题目 ID 删除题目
     * 
     * @param id 题目 ID
     * @return 包含删除结果的响应消息
     */
    @DeleteMapping("/{id}")
    public ResponseMessage<String> deleteQuestion(@PathVariable Integer id) {
        boolean isDeleted = questionService.deleteQuestion(id);
        if (!isDeleted) {
            return ResponseMessage.error(502, "删除题目失败");
        }
        return ResponseMessage.success("题目删除成功");
    }

    /**
     * 获取由指定用户 ID 创建的所有题目
     * 
     * @param uid 用户 ID
     * @return 包含题目输出信息列表的响应消息
     */
    @GetMapping("/createdBy/{uid}")
    public ResponseMessage<List<QuestionOut>> getQuestionsCreatedByUid(@PathVariable String uid) {
        List<QuestionOut> questions = questionService.getQuestionsCreatedByUid(uid);
        if (questions == null || questions.isEmpty()) {
            return ResponseMessage.error(404, "未找到该用户创建的题目");
        }
        return ResponseMessage.success(questions);
    }

    /**
     * 获取由指定用户 ID 更新的所有题目
     * 
     * @param uid 用户 ID
     * @return 包含题目输出信息列表的响应消息
     */
    @GetMapping("/updatedBy/{uid}")
    public ResponseMessage<List<QuestionOut>> getQuestionsUpdatedByUid(@PathVariable String uid) {
        List<QuestionOut> questions = questionService.getQuestionsUpdatedByUid(uid);
        if (questions == null || questions.isEmpty()) {
            return ResponseMessage.error(404, "未找到该用户更新的题目");
        }
        return ResponseMessage.success(questions);
    }

    /**
     * 批量添加题目
     * 
     * @param questions 题目输入信息列表
     * @return 包含添加失败题目输入信息列表的响应消息
     */
    @PostMapping("/batch")
    public ResponseMessage<?> addQuestions(@RequestBody List<QuestionIn> questions) {
        List<QuestionIn> failedQuestions = questionService.addQuestions(questions);
        if (!failedQuestions.isEmpty()) {
            return new ResponseMessage<>(502, "部分题目创建失败", failedQuestions);
        }
        return ResponseMessage.success("题目批量创建成功");
    }
}
