package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizSessionService;
import com.kopivad.testingsystem.service.UserResponseService;
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
    private final UserResponseService responseService;
    private final QuizSessionService quizSessionService;

    @PostMapping(path = "/answer/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteAnswer(Long answerId) {
        answerService.deleteAnswerById(answerId);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveAnswer(AnswerForm answerForm) {
        answerService.saveAnswerFromForm(answerForm);
        return "redirect:/answer/manage";
    }

    @PostMapping(path = "/answer/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAddAnswerPage(Model model) {
        List<Question> allQuestions = questionService.getAllQuestions();
        model.addAttribute("questions", allQuestions);
        return "answerAdd";
    }

    @PostMapping(path = "/answer")
    public String getUserAnswer(UserResponseForm userResponseForm) {
        Question userQuestion = questionService.getQuestionById(userResponseForm.getQuestionId());
        Long currentQuestionNumber = userResponseForm.getQuestionNumber();
        Long totalQuizPage = userResponseForm.getQuestionTotalPages();
        Long currentQuestionQuizId = userQuestion.getQuiz().getId();
        UserResponce userResponse = new UserResponce();
        QuizSession quizSession = quizSessionService.getQuizSessionById(userResponseForm.getSessionId());
        Answer questionAnswer = answerService.getAnswerById(userResponseForm.getUserAnswerId());
        userResponse.setId(-1L);
        userResponse.setQuizSession(quizSession);
        userResponse.setQuestion(userQuestion);
        userResponse.setAnswer(questionAnswer);
        responseService.saveUserResponse(userResponse);
        return currentQuestionNumber.equals(totalQuizPage) ?
                String.format("redirect:/quiz/result/?session=%s" , userResponseForm.getSessionId()) :
                String.format("redirect:/quiz/%d/question/%d?session=%s" , currentQuestionQuizId, currentQuestionNumber + 1, userResponseForm.getSessionId());
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
