package com.example.onlineschool.controller;

import com.example.onlineschool.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/lessons/{id}")
    public String getLesson(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", lessonService.getById(id));
        return "lesson/lesson";
    }
}