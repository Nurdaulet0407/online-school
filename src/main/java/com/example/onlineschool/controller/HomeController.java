package com.example.onlineschool.controller;

import com.example.onlineschool.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "index";
    }
}