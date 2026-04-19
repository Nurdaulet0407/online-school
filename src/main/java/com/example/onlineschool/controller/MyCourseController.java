package com.example.onlineschool.controller;

import com.example.onlineschool.model.User;
import com.example.onlineschool.repository.UserRepository;
import com.example.onlineschool.service.CourseService;
import com.example.onlineschool.service.ProgressService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MyCourseController {

    private final CourseService courseService;
    private final ProgressService progressService;
    private final UserRepository userRepository;

    public MyCourseController(CourseService courseService,
                              ProgressService progressService,
                              UserRepository userRepository) {
        this.courseService = courseService;
        this.progressService = progressService;
        this.userRepository = userRepository;
    }

    @GetMapping("/my-courses")
    public String myCourses(Authentication auth, Model model) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        var courses = courseService.getAllCourses();

        Map<Long, Integer> progressMap = new HashMap<>();
        Map<Long, Integer> percentMap = new HashMap<>();
        Map<Long, String> statusMap = new HashMap<>();
        Map<Long, String> colorMap = new HashMap<>();

        for (var course : courses) {
            int completed = progressService.countCompletedLessons(user, course.getId());

            progressMap.put(course.getId(), completed);

            int percent = (completed * 100) / 8;
            percentMap.put(course.getId(), percent);

            if (completed == 0) {
                statusMap.put(course.getId(), "Не начат");
                colorMap.put(course.getId(), "secondary");
            } else if (completed < 8) {
                statusMap.put(course.getId(), "В процессе");
                colorMap.put(course.getId(), "primary");
            } else {
                statusMap.put(course.getId(), "Курс завершён");
                colorMap.put(course.getId(), "success");
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        model.addAttribute("progressMap", progressMap);
        model.addAttribute("percentMap", percentMap);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("colorMap", colorMap);
        return "course/my-courses";
    }
}