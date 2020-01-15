package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserResponseService responseService;

    @PostMapping(path = "/answer/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAnswer(Long answerId) {
        answerService.deleteAnswerById(answerId);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveAnswer(AnswerForm answerForm) {
        answerService.saveAnswer(answerForm);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editQuiz(AnswerForm answerForm) {
        answerService.updateAnswer(answerForm);
        return "redirect:/answer/manage";
    }

    @SneakyThrows
    @PostMapping(path = "/answer")
    public String getUserAnswer(UserResponseForm userResponseForm) {
        Question userQuestion = questionService.getQuestionById(userResponseForm.getQuestionId());
        Long currentQuestionNumber = userResponseForm.getQuestionNumber();
        Long totalQuizPage = userResponseForm.getQuestionTotalPages();
        Long currentQuestionQuizId = userQuestion.getQuiz().getId();

        responseService.saveUserResponse(userResponseForm);
        return currentQuestionNumber.equals(totalQuizPage) ?
                String.format("redirect:/quiz/result/?session=%s" , userResponseForm.getSessionId()) :
                String.format("redirect:/quiz/%d/question/%d?session=%s" , currentQuestionQuizId, currentQuestionNumber + 1, userResponseForm.getSessionId());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/answer/manage")
    public String getAnswerManagePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("answers", answerService.getAllAnswers());
        model.addAttribute("user", user);
        return "answerManage";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/answer/manage/{id}")
    public String getAllAnswersByQuizIdManagePage(@PathVariable(name = "id") Long id,
                                                  Model model,
                                                  @AuthenticationPrincipal User user) {
        model.addAttribute("answers", answerService.getAnswersByQuestionId(id));
        model.addAttribute("user", user);
        return "answerManage";
    }

    @GetMapping(path = "/answer/edit/{id}")
    public String getEditQuestionPage(@PathVariable(name = "id") Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("answer", answerService.getAnswerById(id));
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("user", user);
        return "answerEdit";
    }

    @GetMapping(path = "/answer/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAddAnswerPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("user", user);
        return "answerAdd";
    }
}
