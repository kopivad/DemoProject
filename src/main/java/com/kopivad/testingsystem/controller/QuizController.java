package com.kopivad.testingsystem.controller;


import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final QuizSessionService quizSessionService;

    @Autowired
    public QuizController(QuizService quizService, QuizSessionService quizSessionService) {
        this.quizService = quizService;
        this.quizSessionService = quizSessionService;
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

    @PostMapping(path = "quiz/add")
    public String saveQuiz(@ModelAttribute QuizForm quizForm) {
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quizForm.getTitle());

        quizService.saveQuiz(newQuiz);
        return "redirect:/index";
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

    @GetMapping(path = "/quiz/{id}/start")
    public String startQuiz(@PathVariable(name = "id") Long id) {
        Quiz currentQuiz = quizService.getQuizById(id).get();
        QuizSession quizSession = new QuizSession();
        quizSession.setQuiz(currentQuiz);
        quizSessionService.createQuizSession(quizSession);
        return String.format("redirect:/quiz/%d/question/1", id);
    }
}
