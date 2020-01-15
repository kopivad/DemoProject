package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.model.*;
import com.kopivad.testingsystem.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class QuizResultController {
    private final QuizService quizService;
    private final QuizResultService quizResultService;
    private final UserResponseService userQuestionResponseService;
    private final QuizSessionService quizSessionService;
    private final QuestionService questionService;
    
    @GetMapping(path = "quiz/result/")
    public String resultPage(@RequestParam(name = "session") Long sessionId, Model model, @AuthenticationPrincipal User user) {
        QuizResult quizResult = quizResultService.saveQuizResult(sessionId, user);
        model.addAttribute("result", quizResult);
        model.addAttribute("user", user);
        return "quizResult";
    }

    @GetMapping(path = "/quiz/check/{session}")
    public String checkResultPage(@PathVariable(name = "session") Long sessionId, Model model, @AuthenticationPrincipal User user) {
        QuizSession quizSession = quizSessionService.getQuizSessionById(sessionId);
        Quiz quiz = quizService.getQuizById(quizSession.getQuiz().getId());
        List<Question> questions = questionService.getQuestionByQuizId(quiz.getId());
        QuizResult quizResult = quizResultService.getQuizResultBySessionId(sessionId);
        List<UserResponse> allResponseSessionId = userQuestionResponseService.getAllResponseBySessionId(sessionId);

        model.addAttribute("quizResult", quizResult);
        model.addAttribute("quizTitle", quiz.getTitle());
        model.addAttribute("allResponses", allResponseSessionId);
        model.addAttribute("questions", questions);
        model.addAttribute("user", user);
        return "quizCheck";
    }

    @GetMapping("/quiz/history/{id}")
    public String quizHistory(@PathVariable(name = "id") Long id, Model model, @AuthenticationPrincipal User user) {
        List<QuizResult> quizResultByUserId = quizResultService.getQuizResultByUserId(id);
        model.addAttribute("results", quizResultByUserId);
        model.addAttribute("user", user);
        return "quizHistory";
    }
}
