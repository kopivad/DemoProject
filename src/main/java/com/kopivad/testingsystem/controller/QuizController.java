package com.kopivad.testingsystem.controller;


import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;

    @PostMapping(path = "quiz/add")
    public String saveQuiz(@AuthenticationPrincipal User user, QuizForm quizForm) {
        quizService.saveQuiz(quizForm, user);
        return "redirect:/index";
    }

    @PostMapping(path = "quiz/edit")
    public String editQuiz(QuizForm quizForm) {
        quizService.updateQuiz(quizForm);
        return "redirect:/quiz/manage";
    }

    @GetMapping(path = "quiz/{id}")
    public String getStartQuizPage(@PathVariable(name = "id") Long quizId, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("quiz", quizService.getQuizById(quizId));
        model.addAttribute("questionAmount", questionService.countByQuizId(quizId));
        model.addAttribute("user", user);
        return "quiz";
    }

    @GetMapping(path = "/quiz/add")
    public String getAddQuizPage(Model model, QuizForm quizForm, @AuthenticationPrincipal User user) {
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("user", user);
        return "quizAdd";
    }

    @GetMapping(path = "/quiz/edit/{id}")
    public String getEditQuizPage(@PathVariable(name = "id") Long id, Model model, QuizForm quizForm, @AuthenticationPrincipal User user) {
        model.addAttribute("quiz", quizService.getQuizById(id));
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("user", user);
        return "quizEdit";
    }

    @GetMapping(path = "/quiz/{id}/start")
    public String startQuiz(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id, Model model) {
        Long sessionId = quizService.startQuiz(id, user);
        model.addAttribute("user", user);
        return String.format("redirect:/quiz/%d/question/1?session=%s", id, sessionId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/quiz/manage")
    public String manageQuiz(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        model.addAttribute("user", user);
        return "quizManage";
    }

    @GetMapping("/quiz/share/{id}")
    public String shareQuiz(@PathVariable(name = "id") Long id, Model model) {
//        model.addAttribute();
        return "quizShare";
    }

    @GetMapping("/quiz/user/{id}")
    public String userQuiz(@PathVariable(name = "id") Long id, Model model) {
//        model.addAttribute();
        return "quizUser";
    }


}
