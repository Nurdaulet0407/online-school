package com.example.onlineschool.service;

import com.example.onlineschool.model.Lesson;
import com.example.onlineschool.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow();
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
}