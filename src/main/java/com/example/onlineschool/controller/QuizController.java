package com.example.onlineschool.controller;

import com.example.onlineschool.model.Question;
import com.example.onlineschool.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.onlineschool.model.Progress;
import com.example.onlineschool.model.User;
import com.example.onlineschool.service.ProgressService;
import com.example.onlineschool.repository.UserRepository;
import org.springframework.security.core.Authentication;
import com.example.onlineschool.service.LessonService;
import com.example.onlineschool.model.Lesson;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {
    private final ProgressService progressService;
    private final UserRepository userRepository;
    private final LessonService lessonService;
    private final QuestionService questionService;

    public QuizController(QuestionService questionService,
                          ProgressService progressService,
                          UserRepository userRepository,
                          LessonService lessonService) {
        this.questionService = questionService;
        this.progressService = progressService;
        this.userRepository = userRepository;
        this.lessonService = lessonService;
    }

    // Показ теста
    @GetMapping("/quiz/{lessonId}")
    public String showQuiz(@PathVariable Long lessonId, Model model) {
        List<Question> questions = questionService.getByLesson(lessonId);
        model.addAttribute("questions", questions);
        model.addAttribute("lessonId", lessonId);
        return "quiz/quiz";
    }

    // Проверка теста
    @PostMapping("/quiz/submit")
    public String submitQuiz(@RequestParam Map<String, String> answers,
                             @RequestParam Long lessonId,
                             Authentication auth,
                             Model model) {

        int score = 0;
        int total = 0;

        Map<String, Boolean> resultMap = new java.util.HashMap<>();

        for (String key : answers.keySet()) {
            if (key.startsWith("q_")) {

                Long questionId = Long.parseLong(key.replace("q_", ""));
                String userAnswer = answers.get(key);

                Question question = questionService.findById(questionId);

                boolean correct = question.getCorrectAnswer().equals(userAnswer);

                if (correct) score++;

                resultMap.put(question.getQuestionText(), correct);
                total++;
            }
        }

        int percent = (total == 0) ? 0 : (score * 100 / total);

        // 👇 СОХРАНЯЕМ ПРОГРЕСС
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        Lesson lesson = lessonService.getById(lessonId); // 👈 добавить

        Progress progress = new Progress();
        progress.setUser(user);
        progress.setLesson(lesson); // 👈 ВАЖНО!
        progress.setScore(score);
        progress.setTotal(total);
        progress.setPercent(percent);
        progress.setCompleted(percent >= 70);

        progressService.save(progress);

        // ===== UI =====
        String resultText;
        String color;

        if (percent >= 90) {
            resultText = "Отлично 🎉";
            color = "success";
        } else if (percent >= 70) {
            resultText = "Хорошо 👍";
            color = "primary";
        } else {
            resultText = "Нужно повторить ❗";
            color = "danger";
        }

        model.addAttribute("score", score);
        model.addAttribute("total", total);
        model.addAttribute("percent", percent);
        model.addAttribute("resultText", resultText);
        model.addAttribute("color", color);
        model.addAttribute("results", resultMap);

        return "quiz/result";
    }
}