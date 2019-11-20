package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.QuestionForm;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;

    @Autowired
    public QuestionController(QuestionService questionService, QuizService quizService) {
        this.questionService = questionService;
        this.quizService = quizService;
    }

    @GetMapping(path = "quiz/{quizId}/question/{n}")
    public String getQuestionByIdAndQuizId(@PathVariable(name = "quizId") Long quizId,
                                           @PathVariable(name = "n") Integer n,
                                           Model model
    ) {
        model.addAttribute("quizId", quizId);
        Pageable pageable = PageRequest.of(n, 1);
        Quiz currentQuiz = quizService.getQuizById(quizId).get();
        Page<Question> question = questionService.getQuestionByQuizId(quizId, pageable);
        model.addAttribute("quiz", currentQuiz);
        model.addAttribute("question", question);

        return "question";
    }


    @PostMapping(path = "/question")
    public String saveQuestion(QuestionForm form) {
        Quiz currentQuiz = quizService.getQuizById(form.getQuizId()).get();
        Question newQuestion = new Question();
        newQuestion.setTitle(form.getTitle());
        newQuestion.setQuiz(currentQuiz);
        questionService.saveQuestion(newQuestion);
        return String.format("redirect:/quiz/%d/question/all", currentQuiz.getId());
    }

    @GetMapping(path = "question/add/{quizId}")
    public String getAddQuestionPage(@PathVariable(name = "quizId") Long id, Model model) {
        model.addAttribute("quizId", id);
        return "questionAdd";
    }


}
