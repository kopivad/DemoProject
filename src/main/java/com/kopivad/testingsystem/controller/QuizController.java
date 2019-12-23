package com.kopivad.testingsystem.controller;


import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizSessionService quizSessionService;

    @PostMapping(path = "quiz/add")
    public String saveQuiz(@AuthenticationPrincipal User user, @ModelAttribute QuizForm quizForm) {
        Quiz newQuiz = new Quiz();
        newQuiz.setId(-1L);
        newQuiz.setTitle(quizForm.getTitle());
        newQuiz.setDescription(quizForm.getDescription());
        newQuiz.setAuthor(user);
        quizService.saveQuiz(newQuiz);
        return "redirect:/index";
    }

    @PostMapping(path = "quiz/edit")
    public String editQuiz(QuizForm quizForm) {
        quizService.updateQuiz(quizForm);
        return "redirect:/quiz/manage";
    }


    @GetMapping(path = "/")
    public String getIndexPage() {
        return "redirect:/index";
    }

    @GetMapping(path = "index")
    public String getQuizPage(Model model, QuizForm quizForm) {
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "index";
    }

    @GetMapping(path = "quiz/{id}")
    public String getStartQuizPage(@PathVariable(name = "id") Long quizId, Model model) {
        model.addAttribute("quiz", quizService.getQuizById(quizId));
        model.addAttribute("questionAmount", questionService.countByQuizId(quizId));
        return "quiz";
    }

    @GetMapping(path = "/quiz/add")
    public String getAddQuizPage(Model model, QuizForm quizForm) {
        model.addAttribute("quizForm", quizForm);
        return "quizAdd";
    }

    @GetMapping(path = "/quiz/edit/{id}")
    public String getEditQuizPage(@PathVariable(name = "id") Long id, Model model, QuizForm quizForm) {
        model.addAttribute("quiz", quizService.getQuizById(id));
        model.addAttribute("quizForm", quizForm);
        return "quizEdit";
    }

    @GetMapping(path = "/quiz/{id}/start")
    public String startQuiz(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id) {
        Long sessionId = quizService.startQuiz(id, user);
        return String.format("redirect:/quiz/%d/question/1?session=%s", id, sessionId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/quiz/manage")
    public String manageQuiz(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quizManage";
    }
}
