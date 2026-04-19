package com.example.onlineschool.service;

import com.example.onlineschool.model.Question;
import com.example.onlineschool.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getByLesson(Long lessonId) {
        return questionRepository.findByLessonId(lessonId);
    }

    public void save(Question q) {
        questionRepository.save(q);
    }
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }
}