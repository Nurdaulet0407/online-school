package com.example.onlineschool.repository;

import com.example.onlineschool.model.Progress;
import com.example.onlineschool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByUser(User user);
    List<Progress> findByUserAndLesson_Course_Id(User user, Long courseId);
}