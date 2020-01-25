package com.kopivad.testingsystem.controller;


import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.Role;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.form.QuizShareForm;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;

    @PostMapping(path = "quiz/add")
    public String saveQuiz(@AuthenticationPrincipal User user, @Valid QuizForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("quizForm", form);
            model.addAttribute("user", user);
            return "quizAdd";
        }

        quizService.saveQuiz(form);
        return "redirect:/index";
    }

    @PostMapping("quiz/share")
    public String shareQuiz(@AuthenticationPrincipal User user, @Valid QuizShareForm shareForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("shareForm", shareForm);
            model.addAttribute("quizId", shareForm.getQuizId());
            model.addAttribute("user", user);
            return "share";
        }
        quizService.shareQuiz(shareForm.getQuizId(), shareForm.getEmail());
        model.addAttribute("message", String.format("Quiz successfully shared on %s", shareForm.getEmail()));
        return "redirect:/index";
    }

    @PostMapping(path = "quiz/edit")
    public String editQuiz(@AuthenticationPrincipal User user, @Valid QuizForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("quizForm", form);
            model.addAttribute("quiz", quizService.getQuizById(form.getQuizId()));
            model.addAttribute("user", user);
            return "quizEdit";
        }
        quizService.updateQuiz(form);
        return "redirect:/quiz/manage";
    }

    @GetMapping(path = "quiz/{id}")
    public String getStartQuizPage(@PathVariable(name = "id") Long quizId, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("quiz", quizService.getQuizById(quizId));
        model.addAttribute("questionAmount", questionService.countByQuizId(quizId));
        model.addAttribute("user", user);
        return "quiz";
    }

    @GetMapping(path = "/quiz/delete")
    public String deleteQuiz(@RequestParam(name = "id") Long quizId, Model model) {
        quizService.deleteQuiz(quizId);
        model.addAttribute("message", "Quiz successfully deleted");
        return "redirect:/index";
    }

    @GetMapping(path = "/quiz/add")
    public String getAddQuizPage(Model model, QuizForm quizForm, @AuthenticationPrincipal User user) {
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("user", user);
        return "quizAdd";
    }

    @GetMapping(path = "/quiz/share")
    public String shareQuizPage(@RequestParam(name = "id") Long id, Model model, QuizForm quizForm, @AuthenticationPrincipal User user) {
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("user", user);
        model.addAttribute("quizId", id);
        return "share";
    }

    @GetMapping(path = "/quiz/edit/")
    public String getEditQuizPage(@RequestParam Long id, Model model, QuizForm quizForm, @AuthenticationPrincipal User user) {
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



    @GetMapping("/quiz/user/{id}")
    public String userQuiz(@PathVariable(name = "id") Long id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzesByUserId(id));
        model.addAttribute("user", user);
        return "quizManage";
    }


}
