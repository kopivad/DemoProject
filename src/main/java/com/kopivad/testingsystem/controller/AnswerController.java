package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.form.UserQuestionResponseForm;
import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.UserQuestionResponseService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserQuestionResponseService responseService;

    @PostMapping(path = "/answer/add")
    public String saveQuestion(AnswerForm answerForm) {
        Question currentQuestion = questionService.getQuestionById(answerForm.getQuestionId());
        Answer newAnswer = new Answer();
        newAnswer.setId(-1L);
        newAnswer.setText(answerForm.getText());
        newAnswer.setRight(answerForm.getIsRight() != null);
        newAnswer.setQuestion(currentQuestion);
        answerService.saveAnswer(newAnswer);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/edit")
    public String editQuiz(AnswerForm answerForm) {
        Answer answerForUpdate = answerService.getAnswerById(answerForm.getAnswerId());
        Question currentQuestion = questionService.getQuestionById(answerForm.getQuestionId());
        answerForUpdate.setRight(answerForm.getIsRight() != null);
        answerForUpdate.setText(answerForm.getText());
        answerForUpdate.setQuestion(currentQuestion);
        answerService.updateAnswer(answerForUpdate);
        return "redirect:/answer/manage";
    }

    @GetMapping(path = "/answer/add")
    public String getAddAnswerPage(Model model) {
        List<Question> allQuestions = questionService.getAllQuestions();
        model.addAttribute("questions", allQuestions);
        return "answerAdd";
    }

    @PostMapping(path = "/answer")
    public String getUserAnswer(UserQuestionResponseForm userResponseForm) {
        Question userQuestion = questionService.getQuestionById(userResponseForm.getQuestionId());
        Long currentQuestionNumber = userResponseForm.getQuestionNumber();
        Long totalQuizPage = userResponseForm.getQuestionTotalPages();
        Long currentQuestionQuizId = userQuestion.getQuiz().getId();
        UserQuestionResponse userResponse = new UserQuestionResponse();
        Answer questionAnswer = answerService.getAnswerById(userResponseForm.getUserAnswerId());
        userResponse.setId(-1L);
        userResponse.setSessionCode(userResponseForm.getSessionCode());
        userResponse.setQuestion(userQuestion);
        userResponse.setAnswer(questionAnswer);
        responseService.saveUserResponse(userResponse);
        return currentQuestionNumber.equals(totalQuizPage) ?
                String.format("redirect:/quiz/result/?code=%s" , userResponseForm.getSessionCode()) :
                String.format("redirect:/quiz/%d/question/%d?code=%s" , currentQuestionQuizId, currentQuestionNumber + 1, userResponseForm.getSessionCode());
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/answer/manage")
    public String getAnswerManagePage(Model model) {
        List<Answer> allAnswers = answerService.getAllAnswers();
        model.addAttribute("answers", allAnswers);
        return "answerManage";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/answer/manage/{id}")
    public String getAllAnswersByQuizIdManagePage(@PathVariable(name = "id") Long id, Model model) {
        List<Answer> allAnswers = answerService.getAnswersByQuestionId(id);
        model.addAttribute("answers", allAnswers);
        return "answerManage";
    }

    @GetMapping(path = "/answer/edit/{id}")
    public String getEditQuestionPage(@PathVariable(name = "id") Long id, Model model) {
        Answer answer = answerService.getAnswerById(id);
        List<Question> allQuestions = questionService.getAllQuestions();
        model.addAttribute("answer", answer);
        model.addAttribute("questions", allQuestions);
        return "answerEdit";
    }
}
