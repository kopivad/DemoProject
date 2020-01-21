package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.UserResponseService;
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
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserResponseService responseService;

    @GetMapping(path = "/answer/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAnswer(@RequestParam(name = "id") Long questionId) {
        answerService.deleteAnswerById(questionId);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveAnswer(@AuthenticationPrincipal User user, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("questions", questionService.getAllQuestions());
            model.addAttribute("user", user);
            model.addAttribute("answerForm", answerForm);
            return "answerAdd";
        }
        answerService.saveAnswer(answerForm);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editQuiz(@AuthenticationPrincipal User user, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("user", user);
            model.addAttribute("answer", answerService.getAnswerById(answerForm.getAnswerId()));
            model.addAttribute("questions", questionService.getAllQuestions());
            return "answerEdit";
        }
        answerService.updateAnswer(answerForm);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer")
    public String getUserAnswer(@Valid UserResponseForm userResponseForm, BindingResult bindingResult) {
        Question userQuestion = questionService.getQuestionById(userResponseForm.getQuestionId());
        Long currentQuestionNumber = userResponseForm.getQuestionNumber();
        Long totalQuizPage = userResponseForm.getQuestionTotalPages();
        Long currentQuestionQuizId = userQuestion.getQuiz().getId();
        if (bindingResult.hasErrors()) {
            return String.format("redirect:/quiz/%d/question/%d?session=%s", currentQuestionQuizId, currentQuestionNumber, userResponseForm.getSessionId());
        }
        responseService.saveUserResponse(userResponseForm);
        return currentQuestionNumber.equals(totalQuizPage) ?
                String.format("redirect:/quiz/result/?session=%s", userResponseForm.getSessionId()) :
                String.format("redirect:/quiz/%d/question/%d?session=%s", currentQuestionQuizId, currentQuestionNumber + 1, userResponseForm.getSessionId());
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
    public String getEditQuestionPage(@PathVariable(name = "id") Long id, Model model, @AuthenticationPrincipal User user, AnswerForm answerForm) {
        model.addAttribute("answer", answerService.getAnswerById(id));
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("user", user);
        model.addAttribute("answerForm", answerForm);
        return "answerEdit";
    }

    @GetMapping(path = "/answer/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAddAnswerPage(Model model, @AuthenticationPrincipal User user, AnswerForm answerForm) {
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("user", user);
        model.addAttribute("answerForm", answerForm);
        return "answerAdd";
    }
}
