package com.kopivad.testingsystem.controller;


import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuizSessionService quizSessionService;

    @PostMapping(path = "quiz/add")
    public String saveQuiz(@ModelAttribute QuizForm quizForm) {
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quizForm.getTitle());
        newQuiz.setDescription(quizForm.getDescription());

        quizService.saveQuiz(newQuiz);
        return "redirect:/index";
    }

    @PostMapping(path = "quiz/edit")
    public String editQuiz(@ModelAttribute QuizForm quizForm) {
        Quiz quizForUpdate = quizService.getQuizById(quizForm.getQuizId()).get();
        quizForUpdate.setTitle(quizForm.getTitle());
        quizForUpdate.setDescription(quizForm.getDescription());
        quizService.saveQuiz(quizForUpdate);
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
        Quiz currentQuiz = quizService.getQuizById(quizId).get();
        model.addAttribute("quiz", currentQuiz);
        return "quiz";
    }

    @GetMapping(path = "/quiz/add")
    public String getAddQuizPage(Model model, QuizForm quizForm) {
        model.addAttribute("quizForm", quizForm);
        return "quizAdd";
    }

    @GetMapping(path = "/quiz/edit/{id}")
    public String getEditQuizPage(@PathVariable(name = "id") Long id, Model model, QuizForm quizForm) {
        Quiz currentQuiz = quizService.getQuizById(id).get();
        model.addAttribute("quiz", currentQuiz);
        model.addAttribute("quizForm", quizForm);
        return "quizEdit";
    }

    @GetMapping(path = "/quiz/{id}/start")
    public String startQuiz(@PathVariable(name = "id") Long id) {
        String sessionCode = UUID.randomUUID().toString();
        Quiz currentQuiz = quizService.getQuizById(id).get();
        QuizSession quizSession = new QuizSession();
        quizSession.setQuiz(currentQuiz);
        quizSession.setCode(sessionCode);
        quizSessionService.createQuizSession(quizSession);
        return String.format("redirect:/quiz/%d/question/1?code=%s", id, sessionCode);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/quiz/manage")
    public String manageQuiz(Model model) {
        List<Quiz> allQuizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", allQuizzes);
        return "quizManage";
    }
}
