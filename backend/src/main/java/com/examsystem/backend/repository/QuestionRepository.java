package com.examsystem.backend.repository;

import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<QuestionOut> findByCreatedByUid(String uid);

    List<QuestionOut> findByUpdatedByUid(String uid);
}
