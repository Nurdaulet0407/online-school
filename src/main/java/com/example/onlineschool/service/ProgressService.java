package com.example.onlineschool.service;

import com.example.onlineschool.model.Progress;
import com.example.onlineschool.model.User;
import com.example.onlineschool.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public void save(Progress progress) {
        progressRepository.save(progress);
    }

    public List<Progress> getByUser(User user) {
        return progressRepository.findByUser(user);
    }
    public int countCompletedLessons(User user, Long courseId) {
        return (int) progressRepository
                .findByUserAndLesson_Course_Id(user, courseId)
                .stream()
                .filter(Progress::isCompleted)
                .count();
    }
}