package com.example.onlineschool.controller;

import com.example.onlineschool.service.CourseService;
import com.example.onlineschool.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;

    public CourseController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping("/courses")
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "course/courses";
    }

    @GetMapping("/courses/{id}")
    public String getCourse(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        model.addAttribute("lessons", lessonService.getLessonsByCourseId(id));
        return "course/course";
    }
}