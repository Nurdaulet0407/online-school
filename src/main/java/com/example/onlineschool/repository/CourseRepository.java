package com.example.onlineschool.repository;

import com.example.onlineschool.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}