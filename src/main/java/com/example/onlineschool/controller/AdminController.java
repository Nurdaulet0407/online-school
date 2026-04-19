package com.example.onlineschool.controller;

import com.example.onlineschool.model.Course;
import com.example.onlineschool.model.Lesson;
import com.example.onlineschool.service.CourseService;
import com.example.onlineschool.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.onlineschool.model.Question;
import com.example.onlineschool.service.QuestionService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final QuestionService questionService;
    private final CourseService courseService;
    private final LessonService lessonService;

    public AdminController(CourseService courseService,
                           LessonService lessonService,
                           QuestionService questionService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.questionService = questionService;
    }

    // 📌 Главная админ панель
    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/admin";
    }

// =========================
// ❓ ДОБАВИТЬ ВОПРОС
// =========================

    @GetMapping("/question/add")
    public String addQuestionPage(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("lessons", lessonService.getAllLessons());
        return "admin/add-question";
    }

    @PostMapping("/question/add")
    public String saveQuestion(@ModelAttribute Question question) {
        questionService.save(question);
        return "redirect:/admin";
    }
    // =========================
    // 📖 ДОБАВИТЬ УРОК
    // =========================

    @GetMapping("/lesson/add")
    public String addLessonPage(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/add-lesson";
    }

    @PostMapping("/lesson/add")
    public String saveLesson(@ModelAttribute Lesson lesson) {
        lessonService.save(lesson);
        return "redirect:/admin";
    }
}